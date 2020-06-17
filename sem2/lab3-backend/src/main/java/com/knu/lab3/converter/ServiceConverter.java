package com.knu.lab3.converter;

import com.knu.lab3.dto.ServiceDTO;
import com.knu.lab3.entity.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ServiceConverter {
    public ServiceDTO convertToDto(Service service) {
        return ServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .build();
    }

    public Service convertToEntity(ServiceDTO serviceDTO) {
        return Service.builder()
                .id(serviceDTO.getId())
                .name(serviceDTO.getName())
                .description(serviceDTO.getDescription())
                .price(serviceDTO.getPrice())
                .build();
    }

    public Set<ServiceDTO> convertToSetDTO(Set<Service> services) {
        return services.stream().map(this::convertToDto).collect(Collectors.toSet());
    }

    public Set<Service> convertToSetEntity(Set<ServiceDTO> services) {
        return services.stream().map(this::convertToEntity).collect(Collectors.toSet());
    }

    public List<ServiceDTO> convertToListDTO(List<Service> services) {
        return services.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
