package com.fabrica.cine.backend.repository;

import com.fabrica.cine.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByActiveTrue();
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByCategoryIgnoreCase(String category);
    List<Movie> findByTitleContainingIgnoreCaseAndCategoryIgnoreCase(String title, String category);

}
