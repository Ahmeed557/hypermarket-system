package com.hypermarket.auth_service.entity;

// الأدوار المختلفة في النظام
public enum Role {
    ADMIN,           // مدير النظام - صلاحيات كاملة
    MANAGER,         // مدير - يدير المنتجات والموظفين
    CASHIER,         // كاشير - يعالج المبيعات
    INVENTORY_STAFF  // موظف مخزن - يدير المخزون
}