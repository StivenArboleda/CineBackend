package com.fabrica.cine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String expiresAt;
    private String role;
    private String id;
}
