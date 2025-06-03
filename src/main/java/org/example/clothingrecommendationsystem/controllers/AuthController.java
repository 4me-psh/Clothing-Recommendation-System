package org.example.clothingrecommendationsystem.controllers;

import org.example.clothingrecommendationsystem.infrastructure.security.JwtUtil;
import org.example.clothingrecommendationsystem.orchestrators.auth.dto.LoginResponseDto;
import org.example.clothingrecommendationsystem.orchestrators.user.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.example.clothingrecommendationsystem.orchestrators.auth.dto.AuthRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${jwt.path-for-register-user}")
    private String targetUrl;
    @Value("${jwt.registration-token}")
    private String internalToken;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RestTemplate restTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthRequest request) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            loginResponseDto.setToken(jwtUtil.generateToken(request.getEmail()));
            return ResponseEntity.ok(loginResponseDto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid login credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDto request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + internalToken);
        HttpEntity<CreateUserDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(targetUrl, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}

