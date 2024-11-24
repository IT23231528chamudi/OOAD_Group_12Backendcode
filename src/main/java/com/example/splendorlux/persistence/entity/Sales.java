package com.example.splendorlux.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = " sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
    private Long salesId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Products productId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "sale_date")
    private Date saleDate;

    @Column(name = "unit_price")
    private Long unitPrice;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name ="status")
    private String status;


    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();
}
