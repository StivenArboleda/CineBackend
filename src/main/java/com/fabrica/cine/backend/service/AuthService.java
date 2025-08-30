package com.fabrica.cine.backend.service;

import com.fabrica.cine.backend.dto.AuthResponseDTO;
import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.dto.LoginRequestDTO;
import com.fabrica.cine.backend.dto.Role;
import com.fabrica.cine.backend.jwt.JwtUtil;
import com.fabrica.cine.backend.mapper.UserMapper;
import com.fabrica.cine.backend.model.User;
import com.fabrica.cine.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String mainRole = roles.isEmpty() ? "CLIENTE" : roles.get(0);
        String username = authentication.getName();

        String token = jwtUtil.generateToken(username, mainRole);
        Date expirationDate = jwtUtil.getExpirationDate(token);

        String expiresAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expirationDate);

        return new AuthResponseDTO(token, expiresAt, mainRole);
    }

    public UserDTO register(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.CLIENTE)
                .build();

        User saved = userRepository.save(user);

        UserDTO result = new UserDTO();
        result.setId(saved.getId());
        result.setEmail(saved.getEmail());
        result.setRole(saved.getRole());
        return result;
    }
}
