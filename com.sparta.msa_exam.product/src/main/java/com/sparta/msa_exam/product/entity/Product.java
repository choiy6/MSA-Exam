package com.sparta.msa_exam.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer supplyPrice;

    public Product(String name, Integer supplyPrice) {
        this.name = name;
        this.supplyPrice = supplyPrice;
    }
}
