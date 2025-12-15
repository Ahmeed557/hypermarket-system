package com.hypermarket.inventory_service.service;

import com.hypermarket.inventory_service.dto.StockMovementRequest;
import com.hypermarket.inventory_service.dto.StockMovementResponse;
import com.hypermarket.inventory_service.entity.StockMovement;
import com.hypermarket.inventory_service.entity.Supplier;
import com.hypermarket.inventory_service.repository.StockMovementRepository;
import com.hypermarket.inventory_service.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final SupplierRepository supplierRepository;
    
    @Transactional
    public StockMovementResponse recordMovement(StockMovementRequest request) {
        StockMovement movement = StockMovement.builder()
                .productId(request.getProductId())
                .productName(request.getProductName())
                .movementType(request.getMovementType())
                .quantity(request.getQuantity())
                .performedBy(request.getPerformedBy())
                .notes(request.getNotes())
                .build();
        
        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            movement.setSupplier(supplier);
        }
        
        movement = stockMovementRepository.save(movement);
        return convertToResponse(movement);
    }
    
    public List<StockMovementResponse> getAllMovements() {
        return stockMovementRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<StockMovementResponse> getMovementsByProduct(Long productId) {
        return stockMovementRepository.findByProductId(productId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<StockMovementResponse> getMovementsBySupplier(Long supplierId) {
        return stockMovementRepository.findBySupplierId(supplierId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private StockMovementResponse convertToResponse(StockMovement movement) {
        return StockMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProductId())
                .productName(movement.getProductName())
                .movementType(movement.getMovementType())
                .quantity(movement.getQuantity())
                .supplierName(movement.getSupplier() != null ? 
                        movement.getSupplier().getName() : null)
                .performedBy(movement.getPerformedBy())
                .notes(movement.getNotes())
                .createdAt(movement.getCreatedAt())
                .build();
    }
}