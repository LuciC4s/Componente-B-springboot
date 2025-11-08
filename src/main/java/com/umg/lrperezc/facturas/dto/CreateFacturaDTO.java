package com.umg.lrperezc.facturas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateFacturaDTO {
    @NotNull
    @Min(1)
    private Long clientId;

    @NotNull
    @Min(1)
    private Long proveedorId;

    private String notes;

    private boolean incluirPendientesDeComponenteA = false;

    @Valid
    @NotEmpty(message = "Debe proporcionar al menos un item")
    private List<ItemDTO> items;
}
