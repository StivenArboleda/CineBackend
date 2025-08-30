package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.service.user.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public UserDTO register(@Valid @RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/registerAdmin")
    @PreAuthorize("permitAll()")
    public UserDTO registerAdmin(@Valid @RequestBody UserDTO userDTO) {
        return userService.registerAdmin(userDTO);
    }

}
