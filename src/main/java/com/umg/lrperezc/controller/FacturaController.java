package com.umg.lrperezc.controller;

import com.umg.lrperezc.dto.CreateFacturaDTO;
import com.umg.lrperezc.dto.FacturaResponseDTO;
import com.umg.lrperezc.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "API para gestión de facturas")
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    @Operation(
            summary = "Crear una nueva factura",
            description = "Registra una nueva factura con los items proporcionados. Opcionalmente puede incluir items pendientes del Componente A."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Factura creada exitosamente",
                    content = @Content(schema = @Schema(implementation = FacturaResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación o datos inválidos",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> crear(@Valid @RequestBody CreateFacturaDTO dto){
        try {
            FacturaResponseDTO resp = facturaService.crearFactura(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar todas las facturas",
            description = "Obtiene una lista de todas las facturas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de facturas obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = FacturaResponseDTO.class))
            )
    })
    public ResponseEntity<List<FacturaResponseDTO>> listar(){
        return ResponseEntity.ok(facturaService.listarFacturas());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una factura por ID",
            description = "Obtiene los detalles de una factura específica mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Factura encontrada",
                    content = @Content(schema = @Schema(implementation = FacturaResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Factura no encontrada",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    public ResponseEntity<?> obtener(
            @Parameter(description = "ID de la factura", required = true, example = "1")
            @PathVariable Long id){
        try {
            return ResponseEntity.ok(facturaService.obtenerFactura(id));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

