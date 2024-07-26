package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.TokenConfirm;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.BadRequestException;
import com.example.booking_movie_ticket.model.enums.Role;
import com.example.booking_movie_ticket.model.enums.TokenType;
import com.example.booking_movie_ticket.model.request.LoginRequest;
import com.example.booking_movie_ticket.model.request.RegisterRequest;
import com.example.booking_movie_ticket.model.response.VerifyResponse;
import com.example.booking_movie_ticket.repository.TokenConfirmRepository;
import com.example.booking_movie_ticket.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticattionManger;
    private final HttpSession session;
    private final TokenConfirmRepository tokenConfirmRepository;
    private final MailService mailService;
    public void login(LoginRequest request){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        try{
            //tien hanh xac thuc
            Authentication authentication = authenticattionManger.authenticate(token);
            //luu doi tuong da xac thuc vao trong securitycontextholder
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //luu vao trong session
            session.setAttribute("MY_SESSION",authentication.getName());
        } catch (DisabledException e){
            throw new BadRequestException("Tài khoản của bạn chưa được kích hoạt vui lòng kiểm tra email");
        } catch (AuthenticationException e){
            throw new BadRequestException("Tài khoản hoặc mật khẩu không đúng");
        }
    }
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exist");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf("USER"))
                .enable(false)
                .build();
        userRepository.save(user);

        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .token(UUID.randomUUID().toString())
                .type(TokenType.CONFIRM_REGISTRATION)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);

        String link = "http://localhost:8099/xac-thuc-tai-khoan?token=" + tokenConfirm.getToken();
//        mailService.sendMail(user.getEmail(), "Xác thực tài khoản", link);
        mailService.sendMail2(user, "Xác thực tài khoản", link);
    }
    public VerifyResponse verifyAccount(String token){
        Optional<TokenConfirm> optionalTokenConfirm=tokenConfirmRepository
                .findByTokenAndType(token,TokenType.CONFIRM_REGISTRATION);

        if (optionalTokenConfirm.isEmpty()){
            return VerifyResponse.builder()
                    .success(false)
                    .message("Token khong hop le")
                    .build();
        }
        TokenConfirm tokenConfirm=optionalTokenConfirm.get();
        if (tokenConfirm.getConfirmedAt()!=null){
            return VerifyResponse.builder()
                    .success(false)
                    .message("Token da duoc xac thuc")
                    .build();
        }

        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())) {
            return VerifyResponse.builder()
                    .success(false)
                    .message("Token da het han")
                    .build();
        }
        User user=tokenConfirm.getUser();
        user.setEnable(true);

        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);
        return VerifyResponse.builder()
                .success(true)
                .message("Xac thuc thanh cong")
                .build();
    }
}

