package com.example.splendorlux.persistence.service;

import com.example.splendorlux.persistence.dto.ProductRequest;
import com.example.splendorlux.persistence.dto.ProductResponse;
import org.springframework.stereotype.Service;
import java.util.List;


public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long productID, ProductRequest productRequest);

    void deleteProduct(Long productID);

    ProductResponse getProductById(Long productID);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> searchProductsByName(String name);
    List<ProductResponse> getProductsByCategoryId(Long categoryId);
    List<ProductResponse> getLatestProducts();
}
