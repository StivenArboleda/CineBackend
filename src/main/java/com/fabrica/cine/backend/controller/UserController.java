package com.fabrica.cine.backend.controller;

import com.fabrica.cine.backend.dto.MovieDTO;
import com.fabrica.cine.backend.dto.UserDTO;
import com.fabrica.cine.backend.service.user.UserServiceImpl;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllPublicMovies() {
        return userService.findAll();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> searchUser(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {
        return userService.searchUser(phone, email);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO findbyid(@Valid @PathVariable Long id){
        return userService.findById(id);
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

    @PutMapping("/disable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO disableMovie(@Valid @PathVariable Long id){
        return userService.disable(id);
    }

    @PutMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO enableMovie(@Valid @PathVariable Long id){
        return userService.enable(id);
    }

}
