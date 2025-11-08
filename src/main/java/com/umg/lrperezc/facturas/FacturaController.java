package com.umg.lrperezc.facturas;

import com.umg.lrperezc.facturas.dto.CreateFacturaDTO;
import com.umg.lrperezc.facturas.dto.FacturaResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CreateFacturaDTO dto){
        try {
            FacturaResponseDTO resp = facturaService.crearFactura(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id){
        try {
            return ResponseEntity.ok(facturaService.obtenerFactura(id));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
