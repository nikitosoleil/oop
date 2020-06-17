package com.knu.lab3.converter;

import com.knu.lab3.dto.UserDTO;
import com.knu.lab3.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ServiceConverter serviceConverter;

    public UserDTO convertToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .blocked(user.getBlocked())
                .services(user.getServices() == null ? null : serviceConverter.convertToSetDTO(user.getServices()))
                .build();
    }

    public User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .blocked(userDTO.getBlocked())
                .services(userDTO.getServices() == null ? null : serviceConverter.convertToSetEntity(userDTO.getServices()))
                .build();
    }

    public List<UserDTO> convertToListDTO(List<User> users) {
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
