package com.umg.lrperezc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con los datos de un proveedor")
public class ProveedorDTO {
    @Schema(description = "ID del proveedor", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del proveedor", example = "Proveedor ABC")
    private String nombre;
    
    @Schema(description = "Email del proveedor", example = "proveedor@example.com")
    private String email;
    
    @Schema(description = "Fecha de creación del proveedor")
    private LocalDateTime createdAt;
}

