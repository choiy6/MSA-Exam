package com.sparta.msa_exam.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String name;
    private Integer supplyPrice;

    public ProductResponseDto(Long productId, String name, Integer supplyPrice) {
        this.productId = productId;
        this.name = name;
        this.supplyPrice = supplyPrice;
    }

    @Override
    public String toString() {
        return "ProductResponseDto{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", supplyPrice=" + supplyPrice +
                '}';
    }

}
