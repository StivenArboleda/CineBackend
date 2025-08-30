package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.AuthResponseDTO;
import com.fabrica.cine.backend.dto.LoginRequestDTO;
import com.fabrica.cine.backend.jwt.JwtUtil;
import com.fabrica.cine.backend.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }
}

