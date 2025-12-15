package com.ceac.gametracker.match.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MatchUpdateRequest(
        @NotNull Result result,
        @Min(0) int score
) {}
