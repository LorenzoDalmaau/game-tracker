package com.ceac.gametracker.config;

import com.ceac.gametracker.user.Role;
import com.ceac.gametracker.user.User;
import com.ceac.gametracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Solo se ejecuta en perfil dev
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Valores configurables (no hardcode)
    private final String adminUsername;
    private final String adminEmail;
    private final String adminPassword;

    public AdminSeeder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.seed.admin.username:admin}") String adminUsername,
            @Value("${app.seed.admin.email:admin@gametracker.com}") String adminEmail,
            @Value("${app.seed.admin.password:admin123}") String adminPassword
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {

        // Si ya existe admin, no hacemos nada
        if (userRepository.existsByUsername(adminUsername) || userRepository.existsByEmail(adminEmail)) {
            return;
        }

        // Hasheamos con BCrypt (como hace el register)
        String hash = passwordEncoder.encode(adminPassword);

        // Creamos admin
        User admin = new User(adminUsername, adminEmail, hash, Role.ADMIN);

        userRepository.save(admin);
    }
}
