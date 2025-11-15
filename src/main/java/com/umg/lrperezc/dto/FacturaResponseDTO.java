package com.umg.lrperezc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con los datos de una factura")
public class FacturaResponseDTO {
    @Schema(description = "ID de la factura", example = "1")
    private Long id;
    
    @Schema(description = "ID del cliente", example = "1")
    private Long clientId;
    
    @Schema(description = "ID del proveedor", example = "1")
    private Long proveedorId;
    
    @Schema(description = "Nombre del proveedor", example = "Proveedor ABC")
    private String proveedorNombre;
    
    @Schema(description = "Notas adicionales", example = "Factura de prueba")
    private String notes;
    
    @Schema(description = "Lista de items de la factura")
    private List<ItemDTO> items;
    
    @Schema(description = "Subtotal de la factura", example = "100.00")
    private BigDecimal subtotal;
    
    @Schema(description = "Total de la factura con IVA", example = "112.00")
    private BigDecimal total;
    
    @Schema(description = "Fecha de creación de la factura")
    private LocalDateTime createdAt;
}

