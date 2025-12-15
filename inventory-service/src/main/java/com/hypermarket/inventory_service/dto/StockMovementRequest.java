package com.hypermarket.inventory_service.dto;

import com.hypermarket.inventory_service.entity.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockMovementRequest {
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @NotBlank(message = "Product name is required")
    private String productName;
    
    @NotNull(message = "Movement type is required")
    private MovementType movementType;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    private Long supplierId;
    
    @NotBlank(message = "Performed by is required")
    private String performedBy;
    
    private String notes;
}