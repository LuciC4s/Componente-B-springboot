package com.umg.lrperezc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO para crear un nuevo proveedor")
public class CreateProveedorDTO {
    @NotBlank
    @Schema(description = "Nombre del proveedor", example = "Proveedor ABC", required = true)
    private String nombre;

    @Email
    @Schema(description = "Email del proveedor (debe ser único si se proporciona)", example = "proveedor@example.com")
    private String email;
}

