package com.hypermarket.inventory_service.entity;

public enum MovementType {
    PURCHASE,   // شراء من مورد
    SALE,       // بيع
    ADJUSTMENT, // تعديل يدوي
    RETURN,     // إرجاع
    DAMAGE      // تالف
}