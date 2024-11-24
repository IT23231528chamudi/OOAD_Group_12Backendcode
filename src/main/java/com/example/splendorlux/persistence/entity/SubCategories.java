package com.example.splendorlux.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subcategories")
public class SubCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private Long subCategoryId;

    @Column(name = "subcategory_name")
    private String subCategoryName;

    // Many subcategories belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Categories category;
}
