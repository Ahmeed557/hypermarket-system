package com.hypermarket.order_service.dto;

import com.hypermarket.order_service.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @NotBlank(message = "Cashier username is required")
    private String cashierUsername;
    
    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemRequest> items;
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}