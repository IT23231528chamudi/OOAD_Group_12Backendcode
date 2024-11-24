package com.example.splendorlux.controller;

import com.example.splendorlux.persistence.dto.SalesRequest;
import com.example.splendorlux.persistence.dto.SalesResponse;
import com.example.splendorlux.persistence.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<SalesResponse>createSales(@RequestBody SalesRequest salesRequest) {
        return ResponseEntity.ok(salesService.createSales(salesRequest));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<SalesResponse>updateSales(
            @PathVariable Long id,
            @PathVariable String status) {
        return ResponseEntity.ok(salesService.updateSalesStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long id){
        salesService.deleteSales(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SalesResponse>> getAllSales() {
        List<SalesResponse> salesResponses = salesService.getAllSales(); // Call service to get all sales
        return ResponseEntity.ok(salesResponses); // Return the list wrapped in ResponseEntity with status 200
    }
}
