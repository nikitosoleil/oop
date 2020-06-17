package com.knu.lab3.service.data;

import com.knu.lab3.entity.User;
import com.knu.lab3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User currentUser) {
        return userRepository.findByEmail(currentUser.getEmail()).orElseGet(() -> userRepository.save(currentUser));
    }
}
