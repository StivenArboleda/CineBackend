package com.fabrica.cine.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MovieDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    private String title;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 25, message = "La categoría no puede superar los 25 caracteres")
    private String category;

    @Min(value = 1900, message = "El año debe ser mayor o igual a 1900")
    @Max(value = 2025, message = "El año debe ser menor o igual a 2025")
    private int year;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String description;

    private boolean active;

    @Positive(message = "La capacidad debe ser mayor que 0")
    private int capacity;

    private String image;
}
