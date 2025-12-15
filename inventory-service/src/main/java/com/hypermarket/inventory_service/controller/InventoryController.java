package com.hypermarket.inventory_service.controller;

import com.hypermarket.inventory_service.dto.*;
import com.hypermarket.inventory_service.service.StockMovementService;
import com.hypermarket.inventory_service.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final SupplierService supplierService;
    private final StockMovementService stockMovementService;
    
    // Supplier endpoints
    @PostMapping("/suppliers")
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.createSupplier(request));
    }
    
    @GetMapping("/suppliers")
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }
    
    @GetMapping("/suppliers/active")
    public ResponseEntity<List<SupplierResponse>> getActiveSuppliers() {
        return ResponseEntity.ok(supplierService.getActiveSuppliers());
    }
    
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }
    
    @PutMapping("/suppliers/{id}")
    public ResponseEntity<SupplierResponse> updateSupplier(@PathVariable Long id, 
                                                           @Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, request));
    }
    
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
    
    // Stock Movement endpoints
    @PostMapping("/movements")
    public ResponseEntity<StockMovementResponse> recordMovement(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockMovementService.recordMovement(request));
    }
    
    @GetMapping("/movements")
    public ResponseEntity<List<StockMovementResponse>> getAllMovements() {
        return ResponseEntity.ok(stockMovementService.getAllMovements());
    }
    
    @GetMapping("/movements/product/{productId}")
    public ResponseEntity<List<StockMovementResponse>> getMovementsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(stockMovementService.getMovementsByProduct(productId));
    }
    
    @GetMapping("/movements/supplier/{supplierId}")
    public ResponseEntity<List<StockMovementResponse>> getMovementsBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(stockMovementService.getMovementsBySupplier(supplierId));
    }
}
