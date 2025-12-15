package com.hypermarket.product_service.Service;

import com.hypermarket.product_service.dto.ProductRequest;
import com.hypermarket.product_service.dto.ProductResponse;
import com.hypermarket.product_service.entity.Category;
import com.hypermarket.product_service.entity.Product;
import com.hypermarket.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.findByBarcode(request.getBarcode()).isPresent()) {
            throw new RuntimeException("Product with this barcode already exists");
        }
        
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .barcode(request.getBarcode())
                .category(request.getCategory())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .minStockLevel(request.getMinStockLevel())
                .active(true)
                .build();
        
        product = productRepository.save(product);
        return convertToResponse(product);
    }
    
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToResponse(product);
    }
    
    public ProductResponse getProductByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToResponse(product);
    }
    
    public List<ProductResponse> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<ProductResponse> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public List<ProductResponse> getLowStockProducts() {
        return productRepository.findByStockQuantityLessThanMinStockLevel().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setMinStockLevel(request.getMinStockLevel());
        
        product = productRepository.save(product);
        return convertToResponse(product);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
    
    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name((String) product.getName())
                .description((String) product.getDescription())
                .barcode((String) product.getBarcode())
                .category((Category) product.getCategory())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .minStockLevel(product.getMinStockLevel())
                .active((Boolean) product.getActive())
                .lowStock(product.getStockQuantity() < product.getMinStockLevel())
                .build();
    }
}
