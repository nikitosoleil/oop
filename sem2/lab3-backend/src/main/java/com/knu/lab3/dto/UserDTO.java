package com.knu.lab3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Name is required")
    private String name;

    private String surname;

    private Boolean blocked;

    private Set<ServiceDTO> services;
}
