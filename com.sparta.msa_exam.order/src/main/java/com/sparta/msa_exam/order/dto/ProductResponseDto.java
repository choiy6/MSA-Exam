package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;  // 상품 ID
    private String name;     // 상품명
    private Integer supplyPrice; // 공급 가격
}
