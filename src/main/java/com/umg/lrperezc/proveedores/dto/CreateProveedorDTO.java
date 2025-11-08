package com.umg.lrperezc.proveedores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProveedorDTO {
    @NotBlank
    private String nombre;

    @Email
    private String email;
}
