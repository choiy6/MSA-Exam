package com.sparta.msa_exam.order.feign;

import com.sparta.msa_exam.order.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallback = ProductServiceFallback.class)
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductResponseDto getProductById(@PathVariable("id") Long id);
}