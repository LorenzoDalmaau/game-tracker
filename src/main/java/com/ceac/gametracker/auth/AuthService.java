package com.ceac.gametracker.auth;

import com.ceac.gametracker.auth.dto.AuthResponse;
import com.ceac.gametracker.auth.dto.AuthResult;
import com.ceac.gametracker.auth.dto.LoginRequest;
import com.ceac.gametracker.auth.dto.RegisterRequest;
import com.ceac.gametracker.config.JwtService;
import com.ceac.gametracker.user.Role;
import com.ceac.gametracker.user.User;
import com.ceac.gametracker.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResult register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            throw new IllegalArgumentException("Username ya existe");
        }
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("¡Email ya existe!");
        }

        String hash = passwordEncoder.encode(req.password());

        User user = new User(req.username(), req.email(), hash, Role.USER);

        User saved = userRepository.save(user);

        String token = jwtService.generateAccesToken(saved.getUsername(), saved.getRole().name());

        return new AuthResult(
                new AuthResponse(saved.getId(), saved.getUsername(), saved.getRole().name()),
                token
        );
    }

    public AuthResult login (LoginRequest req) {
        User user = userRepository.findByUsername(req.usernameOrEmail())
                .or(() -> userRepository.findByEmail(req.usernameOrEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("¡Credenciales inválidas!");
        }

        String token = jwtService.generateAccesToken(user.getUsername(), user.getRole().name());

        return new AuthResult(
                new AuthResponse(user.getId(), user.getUsername(), user.getRole().name()),
                token
        );
    }
}
