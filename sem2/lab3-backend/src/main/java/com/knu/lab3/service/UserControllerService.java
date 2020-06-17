package com.knu.lab3.service;

import com.knu.lab3.converter.UserConverter;
import com.knu.lab3.dto.UserDTO;
import com.knu.lab3.entity.User;
import com.knu.lab3.exception.UserNotFound;
import com.knu.lab3.service.data.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserControllerService {
    private final UserService userService;

    private final UserConverter userConverter;

    public List<UserDTO> findAllUsers() {
        return userConverter.convertToListDTO(userService.findAllUsers());
    }

    @Transactional
    public UserDTO updateBlocked(UserDTO user) {
        Optional<User> oldUser = userService.findUserByEmail(user.getEmail());
        if (!oldUser.isPresent()) {
            throw new UserNotFound("User with email " + user.getEmail() + " not found");
        }
        return userConverter.convertToDto(userService.updateBlocked(oldUser.get(), user.getBlocked()));
    }

}
