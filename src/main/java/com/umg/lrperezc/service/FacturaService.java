package com.umg.lrperezc.service;

import com.umg.lrperezc.dto.CreateFacturaDTO;
import com.umg.lrperezc.dto.FacturaResponseDTO;
import com.umg.lrperezc.dto.ItemDTO;
import com.umg.lrperezc.model.Factura;
import com.umg.lrperezc.model.FacturaItem;
import com.umg.lrperezc.model.Proveedor;
import com.umg.lrperezc.repository.FacturaRepository;
import com.umg.lrperezc.util.ComponentAClient;
import com.umg.lrperezc.OperacionesNegocio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProveedorService proveedorService;
    private final ComponentAClient componentAClient;

    @Transactional
    public FacturaResponseDTO crearFactura(CreateFacturaDTO dto){
        Proveedor proveedor = proveedorService.obtenerPorId(dto.getProveedorId());

        // Unir items de request con posibles items pendientes desde Componente A
        List<ItemDTO> allItems = new ArrayList<>();
        if(dto.getItems() != null){
            allItems.addAll(dto.getItems());
        }
        if(dto.isIncluirPendientesDeComponenteA()){
            try {
                List<ItemDTO> pendientes = componentAClient.obtenerItemsPendientesPorCliente(dto.getClientId());
                if(pendientes != null && !pendientes.isEmpty()){
                    allItems.addAll(pendientes);
                }
            } catch (Exception ignored){
                // Si falla el componente A, continuamos solo con los items provistos
            }
        }
        if(allItems.isEmpty()){
            throw new IllegalArgumentException("Debe proporcionar al menos un item");
        }

        Factura factura = Factura.builder()
                .clientId(dto.getClientId())
                .proveedor(proveedor)
                .notes(dto.getNotes())
                .build();

        BigDecimal subtotal = BigDecimal.ZERO;
        for(ItemDTO itemDTO : allItems){
            FacturaItem item = FacturaItem.builder()
                    .title(itemDTO.getTitle())
                    .price(itemDTO.getPrice())
                    .build();
            factura.addItem(item);
            subtotal = subtotal.add(itemDTO.getPrice());
        }
        factura.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));

        Double totalCalc = OperacionesNegocio.calcularTotalConIVA(subtotal.doubleValue());
        BigDecimal total = totalCalc != null ? BigDecimal.valueOf(totalCalc).setScale(2, RoundingMode.HALF_UP) : null;
        factura.setTotal(total);

        Factura saved = facturaRepository.save(factura);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<FacturaResponseDTO> listarFacturas(){
        return facturaRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public FacturaResponseDTO obtenerFactura(Long id){
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factura con id "+id+" no encontrada"));
        // Recalcular total con la librería para reflejar reglas actuales
        BigDecimal subtotal = factura.getItems().stream()
                .map(FacturaItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Double totalCalc = OperacionesNegocio.calcularTotalConIVA(subtotal.doubleValue());
        BigDecimal total = totalCalc != null ? BigDecimal.valueOf(totalCalc).setScale(2, RoundingMode.HALF_UP) : null;
        FacturaResponseDTO dto = toDTO(factura);
        dto.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        dto.setTotal(total);
        return dto;
    }

    private FacturaResponseDTO toDTO(Factura factura){
        List<ItemDTO> items = factura.getItems().stream().map(it -> {
            ItemDTO dto = new ItemDTO();
            dto.setTitle(it.getTitle());
            dto.setPrice(it.getPrice());
            return dto;
        }).toList();
        return FacturaResponseDTO.builder()
                .id(factura.getId())
                .clientId(factura.getClientId())
                .proveedorId(factura.getProveedor() != null ? factura.getProveedor().getId() : null)
                .proveedorNombre(factura.getProveedor() != null ? factura.getProveedor().getNombre() : null)
                .notes(factura.getNotes())
                .items(items)
                .subtotal(factura.getSubtotal())
                .total(factura.getTotal())
                .createdAt(factura.getCreatedAt())
                .build();
    }
}

