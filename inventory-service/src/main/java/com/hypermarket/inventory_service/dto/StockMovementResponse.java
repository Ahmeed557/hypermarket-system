package com.hypermarket.inventory_service.dto;

import com.hypermarket.inventory_service.entity.MovementType;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponse {
    private Long id;
    private Long productId;
    private String productName;
    private MovementType movementType;
    private Integer quantity;
    private String supplierName;
    private String performedBy;
    private String notes;
    private LocalDateTime createdAt;
}
