package com.hypermarket.product_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

import com.hypermarket.product_service.entity.Category;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Barcode is required")
    private String barcode;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0)
    private Integer stockQuantity;
    
    @NotNull(message = "Minimum stock level is required")
    @Min(value = 0)
    private Integer minStockLevel;

    public Category getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
    }
}