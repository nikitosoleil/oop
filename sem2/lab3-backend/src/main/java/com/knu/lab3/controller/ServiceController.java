package com.knu.lab3.controller;

import com.knu.lab3.dto.ServiceDTO;
import com.knu.lab3.service.ServiceControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceControllerService serviceService;

    @GetMapping(value = "/service")
    public List<ServiceDTO> findAllServices() {
        return serviceService.findAllService();
    }

    @GetMapping(value = "/service/{email}")
    public List<ServiceDTO> findServiceByUser(@PathVariable String email) {
        return serviceService.findServiceByUser(email);
    }

    @PatchMapping(value = "/service/{id}/{email}")
    public ServiceDTO addServiceForUser(@PathVariable Long id, @PathVariable String email) {
        return serviceService.addServiceForUser(id, email);
    }

    @PostMapping(value = "/service")
    public ServiceDTO addService(@Valid @RequestBody ServiceDTO serviceDTO) {
        return serviceService.save(serviceDTO);
    }
}
