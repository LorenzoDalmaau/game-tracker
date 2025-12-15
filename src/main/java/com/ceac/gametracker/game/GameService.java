package com.ceac.gametracker.game;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game create(String name) {
        return gameRepository.save(new Game(name));
    }

    public Game update(Long id, String name) {
        Game g = gameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("este juego no existe"));
        g.rename(name);
        return gameRepository.save(g);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }
}
