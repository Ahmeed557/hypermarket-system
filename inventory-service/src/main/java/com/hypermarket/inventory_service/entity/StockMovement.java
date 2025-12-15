package com.hypermarket.inventory_service.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long productId;  // رقم المنتج
    
    @Column(nullable = false)
    private String productName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;  // نوع الحركة
    
    @Column(nullable = false)
    private Integer quantity;  // الكمية
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @ToString.Exclude
    private Supplier supplier;
    
    @Column(nullable = false)
    private String performedBy;  // من قام بالعملية
    
    @Column(length = 500)
    private String notes;  // ملاحظات
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
