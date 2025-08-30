package com.fabrica.cine.backend.service.user;

import com.fabrica.cine.backend.dto.Role;
import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.mapper.UserMapper;
import com.fabrica.cine.backend.model.User;
import com.fabrica.cine.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDTO register(UserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.CLIENTE);

        User saved = userRepository.save(user);

        UserDTO result = userMapper.toDto(saved);
        result.setPassword(null);
        return result;
    }

    public UserDTO registerAdmin(UserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ADMIN);

        User saved = userRepository.save(user);

        UserDTO result = userMapper.toDto(saved);
        result.setPassword(null);
        return result;
    }

}


