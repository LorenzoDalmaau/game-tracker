package com.ceac.gametracker.match;

import com.ceac.gametracker.game.Game;
import com.ceac.gametracker.match.dto.Result;
import com.ceac.gametracker.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Result result;

    @Column(nullable = false)
    private int score;

    @Column(name = "played_at", nullable = false)
    private Instant playedAt = Instant.now();

    protected Match(){}

    public Match(User user, Game game, Result result, int score) {
        this.user = user;
        this.game = game;
        this.result = result;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public Result getResult() {
        return result;
    }

    public int getScore() {
        return score;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void update(Result result, int score) {
        this.result = result;
        this.score = score;
    }
}
