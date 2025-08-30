package com.fabrica.cine.backend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
}
