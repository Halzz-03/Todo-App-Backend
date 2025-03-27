package com.todo2.todo2.service;

import com.todo2.todo2.model.User;
import com.todo2.todo2.repo.UserRepo;
import com.todo2.todo2.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void register(User user) {
        if (repo.findByName(user.getName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        user.setName(user.getName());
        user.setPassword(encoder.encode(user.getPassword()));

        repo.save(user);
    }

    public Map<String, Object> loginWithTokens(User user) {
        User existingUser = repo.findByName(user.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate access and refresh tokens
        String accessToken = jwtUtil.generateToken(existingUser.getName());
        String refreshToken = jwtUtil.generateRefreshToken(existingUser.getName());

        // Create response with tokens and user info
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken);
        response.put("name", existingUser.getName());

        return response;
    }

    public Map<String, Object> refreshToken(String refreshToken) {
        // Validate refresh token
        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        // Extract username from refresh token
        String username = jwtUtil.extractUsername(refreshToken);

        // Generate new access and refresh tokens
        String newAccessToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        // Create response with new tokens
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken);

        return response;
    }
}