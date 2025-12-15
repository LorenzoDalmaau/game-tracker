package com.ceac.gametracker.match.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MatchCreateRequest(
        @NotNull Long gameId,
        @NotNull Result result,
        @Min(0) int score
) {}
