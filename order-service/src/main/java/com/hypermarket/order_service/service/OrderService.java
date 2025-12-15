package com.hypermarket.order_service.service;

import com.hypermarket.order_service.client.ProductClient;
import com.hypermarket.order_service.client.ProductResponse;
import com.hypermarket.order_service.dto.*;
import com.hypermarket.order_service.entity.Order;
import com.hypermarket.order_service.entity.OrderItem;
import com.hypermarket.order_service.entity.OrderStatus;
import com.hypermarket.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;  // Feign Client
    
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .cashierUsername(request.getCashierUsername())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(BigDecimal.ZERO)
                .build();
        
        BigDecimal total = BigDecimal.ZERO;
        
        // نجيب معلومات المنتجات من Product Service
        for (OrderItemRequest itemRequest : request.getItems()) {
            ProductResponse product = productClient.getProductById(itemRequest.getProductId());
            
            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            
            OrderItem item = OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();
            
            order.addItem(item);
            total = total.add(subtotal);
        }
        
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.COMPLETED);
        
        order = orderRepository.save(order);
        return convertToResponse(order);
    }
    
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponse(order);
    }
    
    public OrderResponse getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponse(order);
    }
    
    public List<OrderResponse> getOrdersByCashier(String cashierUsername) {
        return orderRepository.findByCashierUsername(cashierUsername).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);
        return convertToResponse(order);
    }
    
    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());
        
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomerName())
                .cashierUsername(order.getCashierUsername())
                .items(items)
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
