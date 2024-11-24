package com.example.splendorlux.persistence.repository;

import com.example.splendorlux.persistence.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SalesRepository extends JpaRepository<Sales, Long> {
}
