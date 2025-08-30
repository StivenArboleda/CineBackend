package com.fabrica.cine.backend.service.movie;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.mapper.MovieMapper;
import com.fabrica.cine.backend.model.Movie;
import com.fabrica.cine.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDTO> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    public List<MovieDTO> findAllActive() {
        return movieRepository.findByActiveTrue()
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    public MovieDTO findById(Long id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new RuntimeException("La película no existe"));
    }

    @Override
    public MovieDTO save(MovieDTO movieDTO) {

        System.out.println("DTO recibido: " + movieDTO);
        Movie movie = movieMapper.toEntity(movieDTO);
        System.out.println("Entidad mapeada: " + movie);
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Override
    public MovieDTO update(Long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La película no existe"));

        movie.setTitle(movieDTO.getTitle());
        movie.setCategory(movieDTO.getCategory());
        movie.setYear(movieDTO.getYear());
        movie.setDescription(movieDTO.getDescription());
        movie.setActive(movieDTO.isActive());

        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
