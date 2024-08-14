package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.entity.Actor;
import com.example.booking_movie_ticket.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;
    @GetMapping("/admin/actors")
    public String users(Model model) {
        List<Actor> actors=actorService.getAllActors();
        model.addAttribute("actors",actors);
        return "admin/actor/index";
    }

    @GetMapping("/admin/actors/create")
    public String create(Model model) {
        return "admin/actor/create";
    }

    @GetMapping("/admin/actors/{id}/details")
    public String detail(@PathVariable Integer id,
                         Model model) {
        Actor actor=actorService.getActorDetail(id);
        model.addAttribute("actor",actor);
        return "admin/actor/detail";
    }
}
