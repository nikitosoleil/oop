package com.knu.lab3.controller;

import com.knu.lab3.dto.UserDTO;
import com.knu.lab3.service.UserControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserControllerService userService;

    @GetMapping(value = "/user")
    public List<UserDTO> findAllUsers() {
        return userService.findAllUsers();
    }

    @PatchMapping(value = "/user")
    public UserDTO setUserBlock(@Valid @RequestBody UserDTO user) {
        return userService.updateBlocked(user);
    }
}
