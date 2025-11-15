package com.umg.lrperezc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO que representa un item de factura")
public class ItemDTO {
    @NotBlank
    @Schema(description = "Título del item", example = "Producto XYZ", required = true)
    private String title;

    @NotNull
    @DecimalMin(value = "0.00")
    @Schema(description = "Precio del item", example = "50.00", required = true)
    private BigDecimal price;
}

