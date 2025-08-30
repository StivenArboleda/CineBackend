package com.fabrica.cine.backend.mapper;

import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User customer);
    User toEntity(UserDTO dto);
}
