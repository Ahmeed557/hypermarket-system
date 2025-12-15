package com.hypermarket.inventory_service.repository;

import com.hypermarket.inventory_service.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);
    List<Supplier> findByActive(Boolean active);
    boolean existsByEmail(String email);
}