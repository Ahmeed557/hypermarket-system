package com.hypermarket.product_service.dto;

import com.hypermarket.product_service.entity.Category;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String barcode;
    private Category category;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer minStockLevel;
    private Boolean active;
    private Boolean lowStock;  // تنبيه لو المخزون قليل
}