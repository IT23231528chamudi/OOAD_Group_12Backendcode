package com.example.splendorlux.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_size")
    private String productSize;

    // Many products belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Categories category;

    @Column(name = "stock_quantity")
    private Long stockQuantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "product_image")
    private String productImage;


}
