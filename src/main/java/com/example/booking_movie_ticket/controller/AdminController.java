package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/admin/users")
    public String users(Model model) {
        List<User> users=adminService.getAllUsers();
        model.addAttribute("users",users);
        return "admin/user/index";
    }

    @GetMapping("/admin/users/create")
    public String create(Model model) {
        return "admin/user/create";
    }

    @GetMapping("/admin/users/{id}/details")
    public String detail(@PathVariable Integer id,
                         Model model) {
        User user=adminService.getUserDetail(id);
        model.addAttribute("user",user);
        return "admin/user/detail";
    }

}
