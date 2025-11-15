package com.umg.lrperezc.repository;

import com.umg.lrperezc.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}

