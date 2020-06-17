package com.knu.lab3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDTO {
    @NotNull(message = "Service id is required")
    private Long id;

    private String name;

    private String description;

    private Double price;
}
