package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.exception.ProductNotFoundException;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Value("${server.port}") // 현재 서버의 포트를 주입
    private String serverPort;

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(
            @RequestBody ProductRequestDto requestDto
            ) {

        ProductResponseDto responseDto = productService.addProduct(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Server", "Port-" + serverPort);

        return ResponseEntity.ok().headers(headers).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Server", "Port-" + serverPort);

        return ResponseEntity.ok().headers(headers).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            ProductResponseDto product = productService.getProductById(id);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Server-Port", serverPort);

            return ResponseEntity.ok().headers(headers).body(product);
        } catch (ProductNotFoundException e) {
            // 없는 상품 요청 시 404 응답
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // 그 외의 예외는 500 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
}
