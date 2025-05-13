package com.code.SimpleAuthenticationAPI.service;

import com.code.SimpleAuthenticationAPI.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JdbcUserDetailsManager manager;
    private final BCryptPasswordEncoder passwordEncoder;

    public String register(AuthRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }

        if (manager.userExists(request.getEmail())) {
            return "User already exists";
        }

        User.UserBuilder user = User.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("USER");

        manager.createUser(user.build());
        return "User registered successfully";
    }
}
