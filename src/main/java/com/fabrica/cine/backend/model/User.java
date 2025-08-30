package com.fabrica.cine.backend.model;

import com.fabrica.cine.backend.dto.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "El correo electrónico no es válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 7, max = 10, message = "El teléfono debe tener entre 7 y 10 caracteres")
    @Column(nullable = false, length = 15)
    private String phone;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 15, message = "El nombre no puede superar los 15 caracteres")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 15, message = "El apellido no puede superar los 15 caracteres")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
