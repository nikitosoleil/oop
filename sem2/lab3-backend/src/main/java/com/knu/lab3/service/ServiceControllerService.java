package com.knu.lab3.service;

import com.knu.lab3.converter.ServiceConverter;
import com.knu.lab3.dto.ServiceDTO;
import com.knu.lab3.entity.User;
import com.knu.lab3.exception.ServiceNotFound;
import com.knu.lab3.exception.UserNotFound;
import com.knu.lab3.service.data.ServiceService;
import com.knu.lab3.service.data.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceControllerService {
    private final ServiceService serviceService;
    private final UserService userService;

    private final ServiceConverter serviceConverter;

    public List<ServiceDTO> findAllService() {
        return serviceConverter.convertToListDTO(serviceService.findAllService());
    }

    public ServiceDTO save(ServiceDTO serviceDTO) {
        return serviceConverter.convertToDto(serviceService.save(serviceConverter.convertToEntity(serviceDTO)));
    }

    public List<ServiceDTO> findServiceByUser(String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotFound("User with email " + email + " not found");
        }
        return serviceConverter.convertToListDTO(serviceService.findServiceByUser(user.get()));
    }

    public ServiceDTO addServiceForUser(Long serviceId, String email) {
        Optional<User> user = userService.findUserByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotFound("User with in email " + email + " not found");
        }
        Optional<com.knu.lab3.entity.Service> service = serviceService.findServiceById(serviceId);
        if (!service.isPresent()) {
            throw new ServiceNotFound("Service with id " + serviceId + " not found");
        }
        return serviceConverter.convertToDto(serviceService.addServiceForUser(service.get(), user.get()));
    }
}
