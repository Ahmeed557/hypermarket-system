package com.hypermarket.inventory_service.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean active;
}