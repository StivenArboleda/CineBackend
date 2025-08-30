package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.service.movie.MovieService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public List<MovieDTO> getAllPublicMovies() {
        return movieService.findAll();
    }

    @GetMapping("/public/active")
    public List<MovieDTO> getActiveMovies() {
        return movieService.findAllActive();
    }

    @PostMapping("/movies")
    public MovieDTO createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return movieService.save(movieDTO);
    }

    @PutMapping("/movies/{id}")
    public MovieDTO updateMovie(@Valid @PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        return movieService.update(id, movieDTO);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@Valid @PathVariable Long id) {
        movieService.delete(id);
    }
}

