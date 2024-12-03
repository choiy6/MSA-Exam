package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders") // 테이블 이름 변경
@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> productIds;

    public Order(List<OrderProduct> productIds) {
        productIds.forEach(productId -> productId.setOrder(this));
        this.productIds = productIds;
    }

    // 직렬화 가능한 형태로 변환
    public List<Long> getProductIdsAsList() {
        return productIds.stream().map(OrderProduct::getId).collect(Collectors.toList());
    }
}
