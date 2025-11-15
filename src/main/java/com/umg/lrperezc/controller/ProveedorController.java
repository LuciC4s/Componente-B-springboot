package com.umg.lrperezc.controller;

import com.umg.lrperezc.dto.CreateProveedorDTO;
import com.umg.lrperezc.dto.ProveedorDTO;
import com.umg.lrperezc.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@RequiredArgsConstructor
@Tag(name = "Proveedores", description = "API para gestión de proveedores")
public class ProveedorController {

    private final ProveedorService service;

    @PostMapping
    @Operation(
            summary = "Crear un nuevo proveedor",
            description = "Registra un nuevo proveedor en el sistema. El email debe ser único si se proporciona."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Proveedor creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ProveedorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación o email ya en uso",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> crear(@Valid @RequestBody CreateProveedorDTO dto){
        try {
            ProveedorDTO creado = service.crear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar todos los proveedores",
            description = "Obtiene una lista de todos los proveedores registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de proveedores obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = ProveedorDTO.class))
            )
    })
    public ResponseEntity<List<ProveedorDTO>> listar(){
        return ResponseEntity.ok(service.listar());
    }
}

