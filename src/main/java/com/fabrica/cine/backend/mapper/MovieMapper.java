package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "id", source = "id")
    MovieDTO toDto(Movie movie);

    @Mapping(target = "id", source = "id")
    Movie toEntity(MovieDTO dto);
}
