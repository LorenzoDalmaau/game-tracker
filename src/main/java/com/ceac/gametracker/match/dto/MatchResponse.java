package com.ceac.gametracker.match.dto;


import java.time.Instant;

public record MatchResponse(
        Long id,
        Long gameId,
        String gameName,
        Result result,
        int score,
        Instant playedAt
) {}
