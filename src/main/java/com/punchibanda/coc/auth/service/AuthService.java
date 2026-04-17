package com.punchibanda.coc.auth.service;

import com.punchibanda.coc.auth.dto.AuthResponse;
import com.punchibanda.coc.auth.dto.LoginRequest;
import com.punchibanda.coc.auth.dto.RegisterRequest;
import com.punchibanda.coc.auth.model.User;
import com.punchibanda.coc.auth.repository.UserRepository;
import com.punchibanda.coc.common.exception.DuplicateResourceException;
import com.punchibanda.coc.common.exception.ExternalApiException;
import com.punchibanda.coc.common.exception.ResourceNotFoundException;
import com.punchibanda.coc.config.JWTUtil;
import com.punchibanda.coc.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    private final PlayerService playerService;

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username is already in use");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use");
        }
        if (userRepository.existsByPlayerTag(request.getPlayerTag())) {
            throw new DuplicateResourceException("Player tag is already registered");
        }

        try {
            playerService.getPlayer(request.getPlayerTag());
            playerService.verifyPlayerToken(request.getPlayerTag(), request.getApiToken());
        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ExternalApiException("Invalid player tag or player API token");
        }

        User user = User.builder()
                .username(request.getUsername())
                .playerTag(request.getPlayerTag())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .playerTag(user.getPlayerTag())
                .email(user.getEmail())
                .build();
    }


    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getEmail());

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .playerTag(user.getPlayerTag())
                .email(user.getEmail())
                .build();
    }
}
