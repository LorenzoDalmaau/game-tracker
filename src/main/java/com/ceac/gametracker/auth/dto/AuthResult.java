package com.ceac.gametracker.auth.dto;


public record AuthResult (
        AuthResponse body,
        String token
){}
