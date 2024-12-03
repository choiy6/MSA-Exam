package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.dto.ProductResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.feign.ProductClient;
import com.sparta.msa_exam.order.repository.OrderProductRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductClient productClient;

    public Order addOrder(OrderRequestDto productIds) {
        Order order = new Order(productIds.getProductIds().stream().map(productId->new OrderProduct(productId)).toList());
        return orderRepository.save(order);
    }

    public String addProductToOrder(Long orderId, Long productId) {
        // 상품 유효성 검증
        try {
            ProductResponseDto product = productClient.getProductById(productId);
            if (product == null || product.getProductId() == null || "Unavailable".equals(product.getName())) {
                throw new IllegalArgumentException("Product not found or unavailable");
            }
            // 주문 조회
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            // 주문에 상품 추가
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProductId(productId);
            orderProductRepository.save(orderProduct);

            return "Product successfully added to order";
        } catch (FeignException e) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
    }


    @Cacheable(value = "orders", key = "#id", unless = "#result == null")
    public OrderResponseDto getOrderById(Long id) {
        System.out.println("Fetching from DB...");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 강제 초기화
        if (order.getProductIds() != null) {
            order.getProductIds().size(); // 지연 로딩 강제 초기화
        }
        System.out.println("Order ProductIds: " + order.getProductIds());

        return new OrderResponseDto(order);
    }




}
