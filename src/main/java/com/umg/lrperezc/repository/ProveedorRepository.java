package com.umg.lrperezc.repository;

import com.umg.lrperezc.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    boolean existsByEmail(String email);
    Optional<Proveedor> findByEmail(String email);
}

