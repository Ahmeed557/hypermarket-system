package com.hypermarket.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String orderNumber;  // رقم الطلب
    
    @Column(nullable = false)
    private String customerName;  // اسم العميل
    
    @Column(nullable = false)
    private String cashierUsername;  // اسم الكاشير
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();  // قائمة المنتجات
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;  // الإجمالي
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;  // طريقة الدفع
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;  // حالة الطلب
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        orderNumber = "ORD-" + System.currentTimeMillis();  // إنشاء رقم فريد
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
