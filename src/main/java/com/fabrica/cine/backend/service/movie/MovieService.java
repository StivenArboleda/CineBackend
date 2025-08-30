package com.fabrica.cine.backend.service.movie;

import com.fabrica.cine.backend.dto.MovieDTO;
import java.util.List;

public interface MovieService {

    List<MovieDTO> findAll();
    List<MovieDTO> findAllActive();
    MovieDTO findById(Long id);
    MovieDTO save(MovieDTO movieDTO);
    MovieDTO update(Long id, MovieDTO movieDTO);
    void delete(Long id);
}
