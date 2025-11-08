package com.umg.lrperezc.proveedores;

import com.umg.lrperezc.proveedores.dto.CreateProveedorDTO;
import com.umg.lrperezc.proveedores.dto.ProveedorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository repository;

    @Transactional
    public ProveedorDTO crear(CreateProveedorDTO dto){
        if(dto.getEmail() != null && !dto.getEmail().isBlank() && repository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email ya en uso");
        }
        Proveedor proveedor = Proveedor.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .build();
        Proveedor saved = repository.save(proveedor);
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ProveedorDTO> listar(){
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public Proveedor obtenerPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));
    }

    private ProveedorDTO toDTO(Proveedor p){
        return ProveedorDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .email(p.getEmail())
                .createdAt(p.getCreatedAt())
                .build();
    }
}
