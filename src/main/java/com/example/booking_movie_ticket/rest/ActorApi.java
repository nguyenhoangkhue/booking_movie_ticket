package com.example.booking_movie_ticket.rest;

import com.example.booking_movie_ticket.model.request.*;
import com.example.booking_movie_ticket.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/admin/actors")
@RequiredArgsConstructor
public class ActorApi {
    private final ActorService actorService;

    @PostMapping
    ResponseEntity<?> createMovie(@Valid @RequestBody CreateActorRequest request){
        return ResponseEntity.ok(actorService.createActor(request));
    }
    @PutMapping("/{id}/update")
    ResponseEntity<?> updateMovie(@Valid @RequestBody UpdateActorRequest request,
                                  @PathVariable Integer id){
        return ResponseEntity.ok(actorService.updateActor(request,id));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id){
        actorService.deleteActor(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/upload-avatar")
    ResponseEntity<?> uploadPoster(@PathVariable Integer id, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(actorService.uploadAvatar(id, file));
    }
}
