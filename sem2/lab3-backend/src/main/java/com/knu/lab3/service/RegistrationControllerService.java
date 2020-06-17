package com.knu.lab3.service;

import com.knu.lab3.converter.UserConverter;
import com.knu.lab3.dto.UserDTO;
import com.knu.lab3.service.data.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationControllerService {
    private final UserConverter userConverter;
    private final RegistrationService registrationService;

    @Transactional
    public UserDTO save(UserDTO userDTO) {
        userDTO.setBlocked(false);
        return userConverter.convertToDto(
                registrationService.save(
                        userConverter.convertToEntity(userDTO)));
    }
}
