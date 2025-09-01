package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "active", source = "active")
    UserDTO toDto(User customer);

    @Mapping(target = "active", source = "active")
    User toEntity(UserDTO dto);
}
