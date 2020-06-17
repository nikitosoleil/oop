package com.knu.lab3.controller;

import com.knu.lab3.dto.UserDTO;
import com.knu.lab3.service.RegistrationControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationControllerService registrationService;

    @PostMapping(value = "/registration")
    public UserDTO registration(@Valid @RequestBody UserDTO userDTO){
        return registrationService.save(userDTO);
    }
}
