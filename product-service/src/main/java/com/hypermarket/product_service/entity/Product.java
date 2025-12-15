package com.hypermarket.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;  // اسم المنتج
    
    @Column(length = 1000)
    private String description;  // وصف المنتج
    
    @Column(nullable = false, unique = true)
    private String barcode;  // الباركود (فريد)
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;  // التصنيف
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;  // السعر
    
    @Column(nullable = false)
    private Integer stockQuantity;  // الكمية المتوفرة
    
    @Column(nullable = false)
    private Integer minStockLevel;  // الحد الأدنى للمخزون
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getStockQuantity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStockQuantity'");
    }

    public int getMinStockLevel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMinStockLevel'");
    }

    public Object getActive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActive'");
    }

    public Object getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
    }

    public Object getBarcode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBarcode'");
    }

    public Object getDescription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDescription'");
    }

    public Object getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    public Long getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public void setMinStockLevel(Integer minStockLevel2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMinStockLevel'");
    }

    public void setStockQuantity(Integer stockQuantity2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStockQuantity'");
    }

}