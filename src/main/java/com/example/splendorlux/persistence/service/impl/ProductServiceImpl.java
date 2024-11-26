package com.example.splendorlux.persistence.service.impl;

import com.example.splendorlux.persistence.dto.ProductRequest;
import com.example.splendorlux.persistence.dto.ProductResponse;
import com.example.splendorlux.persistence.entity.Products;
import com.example.splendorlux.persistence.repository.CategoryRepository;
import com.example.splendorlux.persistence.repository.ProductRepository;
import com.example.splendorlux.persistence.service.ProductService;
import com.example.splendorlux.persistence.entity.Categories;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Create a new Products entity and set the values from the ProductRequest DTO
        Products product = new Products();
        product.setProductName(productRequest.getProductName());
        product.setProductSize(productRequest.getProductSize());

        // Assuming Category ID is passed in the request, fetch the Category from the DB
        Categories category = categoryRepository.findById(productRequest.getCategoryID())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);


        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
        product.setProductImage(productRequest.getProductImage());  // Set the product image path

        // Save the product in the repository
        Products savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponse.class);  // Mapping directly to the response DTO
    }

    @Override
    public ProductResponse updateProduct(Long productID, ProductRequest productRequest) {
        // Fetch existing product
        Products product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update the product's details
        product.setProductName(productRequest.getProductName());
        product.setProductSize(productRequest.getProductSize());

        // Update category if category ID is provided
        if (productRequest.getCategoryID() != null) {
            Categories category = new Categories();
            category.setCategoryId(productRequest.getCategoryID());
            product.setCategory(category);  // Set the Category object
        }

        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
        product.setProductImage(productRequest.getProductImage());

        // Save updated product and return the response
        Products updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }

    @Override
    public List<ProductResponse> searchProductsByName(String name) {
        // Fetch products from the repository using a custom query
        List<Products> products = productRepository.findByProductNameContainingIgnoreCase(name);

        // Map products to ProductResponse DTO
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        // Fetch products by category ID
        List<Products> products = productRepository.findByCategory_CategoryId(categoryId);

        // Map products to ProductResponse DTO
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }


    @Override
    public void deleteProduct(Long productID) {
        // Delete the product by its ID
        productRepository.deleteById(productID);
    }

    @Override
    public ProductResponse getProductById(Long productID) {
        // Retrieve product by ID
        Products product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductResponse.class);  // Return the response DTO
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        // Get all products and map them to ProductResponse DTO
        List<Products> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getLatestProducts() {
        List<Products> products = productRepository.findTop4ByOrderByProductIDDesc();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }
}
