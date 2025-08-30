package com.fabrica.cine.backend.service.user;

import com.fabrica.cine.backend.dto.UserDTO;

public interface UserService {
    UserDTO register(UserDTO dto);
}