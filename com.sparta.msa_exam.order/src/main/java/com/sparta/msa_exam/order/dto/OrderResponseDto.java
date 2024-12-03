package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDto implements Serializable { // Serializable 추가
    private Long orderId;
    private List<Long> productIds;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.productIds = order.getProductIds() != null && !order.getProductIds().isEmpty()
                ? order.getProductIds().stream().map(OrderProduct::getProductId).toList()
                : List.of(); // null 방어 로직
        System.out.println("OrderResponseDto: " + this.productIds);

    }

}

