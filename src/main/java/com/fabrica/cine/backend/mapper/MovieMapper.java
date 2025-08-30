package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toDto(Movie movie);
    Movie toEntity(MovieDTO dto);
}
