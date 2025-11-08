package com.umg.lrperezc.facturas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    @NotBlank
    private String title;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal price;
}
