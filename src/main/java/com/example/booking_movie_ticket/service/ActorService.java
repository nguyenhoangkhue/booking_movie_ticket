package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.Actor;
import com.example.booking_movie_ticket.exception.ResourceNotFoundException;
import com.example.booking_movie_ticket.model.request.CreateActorRequest;
import com.example.booking_movie_ticket.model.request.UpdateActorRequest;
import com.example.booking_movie_ticket.repository.*;
import com.github.slugify.Slugify;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    private final CloudinaryService cloudinaryService;
    public Actor getActorDetail(Integer id){
        return actorRepository.findActorById(id).orElse(null);
    }
    public List<Actor> getAllActors(){
        return actorRepository.findAll(Sort.by("createdAt").descending());
    }
    Slugify slugify= Slugify.builder().build();
    public Actor createActor(CreateActorRequest request){
        Actor actor=Actor.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .avatar("https://placehold.co/600x400?text="+request.getName().substring(0,1).toLowerCase())
                .bio(request.getBio())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return actorRepository.save(actor);
    }
    public Actor updateActor(@Valid UpdateActorRequest request, Integer id){
        Actor actor = actorRepository.findActorById(id).orElseThrow(()->new ResourceNotFoundException("Actor not found"));
        actor.setName(request.getName());
        actor.setSlug(slugify.slugify(request.getName()));
        actor.setBio(request.getBio());
        actor.setUpdatedAt(LocalDateTime.now());
        return actorRepository.save(actor);
    }
    public void deleteActor(Integer id) {
        Actor actor=actorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Actor not found"));
        actorRepository.deleteById(id);
    }
    public String uploadAvatar(Integer id, MultipartFile file){
        Actor actor=actorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy diễn viên"));
        try{
            Map result=cloudinaryService.uploadFile(file);
            System.out.println(result);
            actor.setAvatar((String) result.get("url"));
            return (String) result.get("url");
        }catch (Exception e){
            throw new RuntimeException("Lỗi khi upload Avatar");
        }
    }
}
