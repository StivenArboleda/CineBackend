package com.fabrica.cine.backend.service.movie;

import com.fabrica.cine.backend.dto.MovieDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MovieService {

    List<MovieDTO> findAll();
    List<MovieDTO> findAllActive();
    MovieDTO findById(Long id);
    MovieDTO save(String movieDTO, MultipartFile imageFile);
    MovieDTO update(Long id, String movieJson, MultipartFile imageFile);
    MovieDTO disable(Long id);
    MovieDTO enable(Long id);
    void delete(Long id);
}
