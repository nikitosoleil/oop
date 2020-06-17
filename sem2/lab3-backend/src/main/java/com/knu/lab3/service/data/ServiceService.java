package com.knu.lab3.service.data;

import com.knu.lab3.entity.User;
import com.knu.lab3.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public List<com.knu.lab3.entity.Service> findAllService() {
        return serviceRepository.findAll();
    }

    public com.knu.lab3.entity.Service save(com.knu.lab3.entity.Service service) {
        return serviceRepository.save(service);
    }

    public List<com.knu.lab3.entity.Service> findServiceByUser(User user) {
        List<Long> userServices =
                user.getServices().stream().map(com.knu.lab3.entity.Service::getId).collect(Collectors.toList());
        if (userServices.isEmpty()) {
            return serviceRepository.findAll();
        }
        return serviceRepository.findByIdNotIn(user.getServices().stream().map(com.knu.lab3.entity.Service::getId).collect(Collectors.toList()));
    }

    public Optional<com.knu.lab3.entity.Service> findServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId);
    }

    @Transactional
    public com.knu.lab3.entity.Service addServiceForUser(com.knu.lab3.entity.Service service, User user) {
        user.getServices().add(service);
        return service;
    }
}
