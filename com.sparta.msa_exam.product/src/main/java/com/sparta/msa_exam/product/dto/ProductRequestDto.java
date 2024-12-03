package com.sparta.msa_exam.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private Integer supplyPrice;
}
