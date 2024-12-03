package com.sparta.msa_exam.order.feign;

import com.sparta.msa_exam.order.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

public class ProductServiceFallback implements ProductClient {
    @Override
    public ProductResponseDto getProductById(Long id) {
        // Fallback 동작: 상품이 없다고 응답
        return new ProductResponseDto(null, "Unavailable", null);
    }
}