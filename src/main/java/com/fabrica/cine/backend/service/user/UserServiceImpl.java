package com.fabrica.cine.backend.service.user;

import com.fabrica.cine.backend.dto.Role;
import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.mapper.UserMapper;
import com.fabrica.cine.backend.model.User;
import com.fabrica.cine.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .peek(userDTO -> userDTO.setPassword(null))
                .toList();
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("La película no existe"));
    }

    public List<UserDTO> searchUser(String phone, String email) {

        List<User> users;

        if (phone != null && email != null) {
            log.info("Buscando películas con phone {} y email {}", phone, email);
            users = userRepository.findByEmailContainingIgnoreCaseAndPhoneIgnoreCase(phone, email);
        } else if (phone != null) {
            log.info("Buscando películas con phone {} ", phone);
            users = userRepository.findByPhoneContainingIgnoreCase(phone);
        } else if (email != null) {
            log.info("Buscando películas con email {}", email);
            users = userRepository.findByEmailIgnoreCase(email);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO register(UserDTO dto) {


        log.info("Registrando un usuario: {}", dto);

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.CLIENTE);
        user.setActive(true);

        User saved = userRepository.save(user);

        UserDTO result = userMapper.toDto(saved);
        result.setPassword(null);
        log.info("Registrado correctamente: {}", dto);
        return result;
    }

    public UserDTO registerAdmin(UserDTO dto) {

        log.info("Registrando un admin: {}", dto);
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ADMIN);
        user.setActive(true);

        User saved = userRepository.save(user);

        UserDTO result = userMapper.toDto(saved);
        result.setPassword(null);
        log.info("Registrado correctamente: {}", dto);
        return result;
    }

    @Override
    public UserDTO disable(Long id){

        log.info("Desactivando un usuario: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        user.setActive(false);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO enable(Long id){
        log.info("Activando un usuario: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        user.setActive(true);

        return userMapper.toDto(userRepository.save(user));
    }


}


