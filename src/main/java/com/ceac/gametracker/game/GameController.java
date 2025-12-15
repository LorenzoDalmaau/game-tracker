package com.ceac.gametracker.game;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    /// DTOs
    public record GameResponse(Long id, String name){}
    public record GameRequest(@NotBlank String name){}

    @GetMapping
    public List<GameResponse> list() {
        return service.findAll().stream()
                .map(g -> new GameResponse(g.getId(), g.getName()))
                .toList();
    }

    @PostMapping
    public GameResponse create (@RequestBody GameRequest req) {
        Game g = service.create(req.name);
        return new GameResponse(g.getId(), g.getName());
    }

    @PutMapping("/{id}")
    public GameResponse update(@PathVariable Long id, @RequestBody GameResponse req) {
        Game g = service.update(id, req.name);
        return new GameResponse(g.getId(), g.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
