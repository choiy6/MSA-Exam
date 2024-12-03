package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "order_product")
@Getter
@Setter
@NoArgsConstructor
public class OrderProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId; // 상품 ID

    public OrderProduct(Long productId) {
        this.productId = productId;
    }
}
