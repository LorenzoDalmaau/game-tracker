package com.ceac.gametracker.game;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "games")
public class Game {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected Game() {}

    public Game(String name) { this.name = name; }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void rename(String name) { this.name = name; }
}
