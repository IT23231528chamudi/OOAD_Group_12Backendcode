package com.example.splendorlux.persistence.service;

import com.example.splendorlux.persistence.dto.SalesRequest;
import com.example.splendorlux.persistence.dto.SalesResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SalesService {
    SalesResponse createSales(SalesRequest salesRequest);

    SalesResponse updateSales(Long salesID, SalesRequest salesRequest);

    void deleteSales(Long salesID);

    SalesResponse getSalesById(Long productID);

    SalesResponse updateSalesStatus(Long salesId, String status);
    List<SalesResponse> getAllSales();
}
