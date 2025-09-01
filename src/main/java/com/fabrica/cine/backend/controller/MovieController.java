package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.service.movie.MovieService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    @PermitAll
    public List<MovieDTO> getAllPublicMovies() {
        return movieService.findAll();
    }

    @GetMapping("/movies/active")
    @PreAuthorize("hasRole('CLIENTE')")
    public List<MovieDTO> getActiveMovies() {
        return movieService.findAllActive();
    }

    @PostMapping(value = "/movies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public MovieDTO createMovie(
            @RequestPart("movie") String movieJson,
            @RequestPart("image") MultipartFile imageFile) {

        return movieService.save(movieJson, imageFile);
    }

    @PutMapping("/movies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MovieDTO updateMovie(@Valid @PathVariable Long id, @RequestPart("movie") String movieJson,
                                @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return movieService.update(id, movieJson, imageFile);
    }

    @DeleteMapping("/movies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(@Valid @PathVariable Long id) {
        movieService.delete(id);
    }

    @GetMapping("/movies/search")
    public List<MovieDTO> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category) {
        return movieService.searchMovies(title, category);
    }

    @PutMapping("/movies/disable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MovieDTO disableMovie(@Valid @PathVariable Long id){
        return movieService.disable(id);
    }

    @PutMapping("/movies/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MovieDTO enableMovie(@Valid @PathVariable Long id){
        return movieService.enable(id);
    }

}

