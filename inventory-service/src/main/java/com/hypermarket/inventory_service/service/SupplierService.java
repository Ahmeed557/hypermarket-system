package com.hypermarket.inventory_service.service;

import com.hypermarket.inventory_service.dto.SupplierRequest;
import com.hypermarket.inventory_service.dto.SupplierResponse;
import com.hypermarket.inventory_service.entity.Supplier;
import com.hypermarket.inventory_service.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    
    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request) {
        if (supplierRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Supplier with this email already exists");
        }
        
        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .active(true)
                .build();
        
        supplier = supplierRepository.save(supplier);
        return convertToResponse(supplier);
    }
    
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<SupplierResponse> getActiveSuppliers() {
        return supplierRepository.findByActive(true).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return convertToResponse(supplier);
    }
    
    @Transactional
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAddress(request.getAddress());
        
        supplier = supplierRepository.save(supplier);
        return convertToResponse(supplier);
    }
    
    @Transactional
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        supplierRepository.deleteById(id);
    }
    
    private SupplierResponse convertToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phoneNumber(supplier.getPhoneNumber())
                .address(supplier.getAddress())
                .active(supplier.getActive())
                .build();
    }
}