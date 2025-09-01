package com.fabrica.cine.backend.service.user;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO register(UserDTO dto);
    UserDTO disable(Long id);
    UserDTO enable(Long id);
}