package com.hypermarket.order_service.dto;

import com.hypermarket.order_service.entity.OrderStatus;
import com.hypermarket.order_service.entity.PaymentMethod;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String cashierUsername;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private LocalDateTime createdAt;
}