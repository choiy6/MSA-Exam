package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.exception.ProductNotFoundException;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDto addProduct(ProductRequestDto requestDto) {
        Product product = new Product(requestDto.getName(), requestDto.getSupplyPrice());
        Product savedProduct = productRepository.save(product);

        return new ProductResponseDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getSupplyPrice()
        );
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getSupplyPrice()))
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found."));
        return new ProductResponseDto(product.getId(), product.getName(), product.getSupplyPrice());
    }
}
