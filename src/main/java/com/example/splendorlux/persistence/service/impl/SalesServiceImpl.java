package com.example.splendorlux.persistence.service.impl;

import com.example.splendorlux.persistence.dto.SalesRequest;
import com.example.splendorlux.persistence.dto.SalesResponse;
import com.example.splendorlux.persistence.entity.Products;
import com.example.splendorlux.persistence.entity.Sales;
import com.example.splendorlux.persistence.repository.ProductRepository;
import com.example.splendorlux.persistence.repository.SalesRepository;
import com.example.splendorlux.persistence.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);

    @Override
    public SalesResponse createSales(SalesRequest salesRequest) {
        Sales sales = new Sales();

        // Fetch product and set it in the sales entity
        Products product = productRepository.findById(salesRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        sales.setProductId(product);

        // Map the rest of the fields
        sales.setQuantity(salesRequest.getQuantity());
        sales.setSaleDate(salesRequest.getSaleDate());
        sales.setUnitPrice(salesRequest.getUnitPrice());
        sales.setTotalPrice(salesRequest.getTotalPrice());
        sales.setStatus(salesRequest.getStatus() != null ? salesRequest.getStatus() : "pending"); // Set default status if not provided

        Sales savedSales = salesRepository.save(sales);

        // Manually map to SalesResponse
        return mapToResponse(savedSales);
    }

    @Override
    public SalesResponse updateSales(Long salesID, SalesRequest salesRequest) {
        // Fetch the sales entity
        Sales existingSales = salesRepository.findById(salesID)
                .orElseThrow(() -> new RuntimeException("Sales not found"));

        // Update only the status field
        if (salesRequest.getStatus() != null) {
            existingSales.setStatus(salesRequest.getStatus());
        }

        Sales updatedSales = salesRepository.save(existingSales);

        // Manually map to SalesResponse
        return mapToResponse(updatedSales);
    }

    @Override
    public SalesResponse updateSalesStatus(Long salesId, String status) {
        Sales sales = salesRepository.findById(salesId)
                .orElseThrow(() -> new RuntimeException("Sales not found"));

        // Update only the status field
        sales.setStatus(status);

        Sales updatedSales = salesRepository.save(sales);

        // Map to SalesResponse
        return mapToResponse(updatedSales);
    }


    @Override
    public void deleteSales(Long salesID) {
        salesRepository.deleteById(salesID);
    }

    @Override
    public SalesResponse getSalesById(Long salesID) {
        Sales sale = salesRepository.findById(salesID)
                .orElseThrow(() -> new RuntimeException("Sales not found"));

        // Map to SalesResponse
        return mapToResponse(sale);
    }

    @Override
    public List<SalesResponse> getAllSales() {
        List<Sales> salesList = salesRepository.findAll();

        // Log the sales and associated products
        for (Sales sale : salesList) {
            logger.info("Sale ID: " + sale.getSalesId());
            if (sale.getProductId() != null) {
                logger.info("Product ID: " + sale.getProductId().getProductID());
                logger.info("Product Name: " + sale.getProductId().getProductName());
                logger.info("Product Size: " + sale.getProductId().getProductSize());
                logger.info("Product Image: " + sale.getProductId().getProductImage());
            }
        }

        // Convert each Sales entity to SalesResponse
        return salesList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper method to map Sales entity to SalesResponse
    private SalesResponse mapToResponse(Sales sales) {
        SalesResponse response = new SalesResponse();
        response.setSalesId(sales.getSalesId());
        response.setProductId(sales.getProductId().getProductID());
        response.setQuantity(sales.getQuantity());
        response.setSaleDate(sales.getSaleDate());
        response.setUnitPrice(sales.getUnitPrice());
        response.setTotalPrice(sales.getTotalPrice());
        response.setStatus(sales.getStatus()); // Include the status field in the response
        return response;
    }
}
