package com.fabrica.cine.backend.service.movie;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.mapper.MovieMapper;
import com.fabrica.cine.backend.model.Movie;
import com.fabrica.cine.backend.repository.MovieRepository;
import com.fabrica.cine.backend.service.FirebaseStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final FirebaseStorageService firebaseStorageService;

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
    public MovieDTO save(String movieJson, MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MovieDTO movieDTO = objectMapper.readValue(movieJson, MovieDTO.class);

            String imageUrl = firebaseStorageService.uploadFile(imageFile);
            movieDTO.setImage(imageUrl);

            Movie movie = movieMapper.toEntity(movieDTO);
            Movie saved = movieRepository.save(movie);

            return movieMapper.toDto(saved);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la película", e);
        }
    }

    @Override
    public MovieDTO update(Long id, String movieJson, MultipartFile imageFile) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            MovieDTO movieDTO = objectMapper.readValue(movieJson, MovieDTO.class);

            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La película no existe"));

            movie.setTitle(movieDTO.getTitle());
            movie.setCategory(movieDTO.getCategory());
            movie.setYear(movieDTO.getYear());
            movie.setDescription(movieDTO.getDescription());
            movie.setActive(movieDTO.isActive());
            movie.setCapacity(movieDTO.getCapacity());

            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = firebaseStorageService.uploadFile(imageFile);
                movie.setImage(imageUrl);
            }

            Movie updated = movieRepository.save(movie);
            return movieMapper.toDto(updated);

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la película", e);
        }
    }

    @Override
    public MovieDTO disable(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La película no existe"));

        movie.setActive(false);

        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Override
    public MovieDTO enable(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La película no existe"));

        movie.setActive(true);

        return movieMapper.toDto(movieRepository.save(movie));
    }

    public List<MovieDTO> searchMovies(String title, String category) {
        List<Movie> movies;

        if (title != null && category != null) {
            movies = movieRepository.findByTitleContainingIgnoreCaseAndCategoryIgnoreCase(title, category);
        } else if (title != null) {
            movies = movieRepository.findByTitleContainingIgnoreCase(title);
        } else if (category != null) {
            movies = movieRepository.findByCategoryIgnoreCase(category);
        } else {
            movies = movieRepository.findAll();
        }

        return movies.stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
