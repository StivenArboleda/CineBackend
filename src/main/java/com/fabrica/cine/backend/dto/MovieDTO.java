package com.fabrica.cine.backend.dto;

import lombok.Data;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String category;
    private int year;
    private String description;
    private boolean active;
    private int capacity;
}
