package com.shop.service;

import com.shop.dto.auth.JwtAuthResponse;
import com.shop.dto.auth.LoginRequest;
import com.shop.dto.auth.RegisterRequest;
import com.shop.entity.User;
import com.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthResponse signUp(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder()
                .token(jwt)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    public JwtAuthResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthResponse.builder()
                .token(jwt)
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}
