package com.umg.lrperezc.proveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDateTime createdAt;
}
