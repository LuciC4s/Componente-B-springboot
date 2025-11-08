package com.umg.lrperezc.proveedores;

import com.umg.lrperezc.proveedores.dto.CreateProveedorDTO;
import com.umg.lrperezc.proveedores.dto.ProveedorDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody CreateProveedorDTO dto){
        try {
            ProveedorDTO creado = service.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public List<ProveedorDTO> listar(){
        return service.listar();
    }
}
