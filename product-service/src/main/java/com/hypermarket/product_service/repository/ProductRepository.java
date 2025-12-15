package com.hypermarket.product_service.repository;

import com.hypermarket.product_service.entity.Category;
import com.hypermarket.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByBarcode(String barcode);
    List<Product> findByCategory(Category category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByStockQuantityLessThanMinStockLevel();
}