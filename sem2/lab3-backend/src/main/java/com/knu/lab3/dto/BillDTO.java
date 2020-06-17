package com.knu.lab3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDTO {
    @NotNull(message = "Bill id is required")
    private Long id;

    private Date date;

    @NotNull(message = "Bill price is required")
    private Double price;

    private Boolean paid;

    private String email;
}
