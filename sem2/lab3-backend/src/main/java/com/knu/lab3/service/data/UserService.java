package com.knu.lab3.service.data;

import com.knu.lab3.entity.User;
import com.knu.lab3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateBlocked(User user, Boolean blocked) {
        return user.toBuilder().blocked(blocked).build();
    }
}
