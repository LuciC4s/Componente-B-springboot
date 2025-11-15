package com.umg.lrperezc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "DTO para crear una nueva factura")
public class CreateFacturaDTO {
    @NotNull
    @Min(1)
    @Schema(description = "ID del cliente", example = "1", required = true)
    private Long clientId;

    @NotNull
    @Min(1)
    @Schema(description = "ID del proveedor", example = "1", required = true)
    private Long proveedorId;

    @Schema(description = "Notas adicionales de la factura", example = "Factura de prueba")
    private String notes;

    @Schema(description = "Indica si se deben incluir items pendientes del Componente A", example = "false")
    private boolean incluirPendientesDeComponenteA = false;

    @Valid
    @NotEmpty(message = "Debe proporcionar al menos un item")
    @Schema(description = "Lista de items de la factura", required = true)
    private List<ItemDTO> items;
}

