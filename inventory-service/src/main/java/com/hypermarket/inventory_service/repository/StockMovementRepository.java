package com.hypermarket.inventory_service.repository;

import com.hypermarket.inventory_service.entity.MovementType;
import com.hypermarket.inventory_service.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductId(Long productId);
    List<StockMovement> findByMovementType(MovementType movementType);
    List<StockMovement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<StockMovement> findBySupplierId(Long supplierId);
}
