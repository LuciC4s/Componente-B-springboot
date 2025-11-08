package com.umg.lrperezc.facturas.dto;

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
public class FacturaResponseDTO {
    private Long id;
    private Long clientId;
    private Long proveedorId;
    private String proveedorNombre;
    private String notes;
    private List<ItemDTO> items;
    private BigDecimal subtotal;
    private BigDecimal total;
    private LocalDateTime createdAt;
}
