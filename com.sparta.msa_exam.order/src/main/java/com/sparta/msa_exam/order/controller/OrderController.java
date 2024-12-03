package com.sparta.msa_exam.order.controller;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.dto.ProductResponseDto;
import com.sparta.msa_exam.order.feign.ProductClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value; // 이 부분 수정
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ProductClient productClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody OrderRequestDto productIds) {
        Order order = orderService.addOrder(productIds);
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Server", "Port-" + serverPort);

        return ResponseEntity.ok().headers(headers).body(orderResponseDto);
    }

    // 상품 서비스 호출 실패 시 fallback 처리
    @PostMapping(params = "fail")
    public ResponseEntity<String> addOrderWithFail(@RequestBody OrderRequestDto requestDto) {
        try {
            // 상품 서비스 호출을 시도하여 강제로 실패 케이스를 유도
            for (Long productId : requestDto.getProductIds()) {
                ProductResponseDto product = productClient.getProductById(productId);

                // Fallback 호출을 확인하려면 특정 조건에서 의도적으로 예외를 발생
                if (product.getProductId() == null || product.getName().equals("Unavailable")) {
                    throw new RuntimeException("Product API failure triggered.");
                }
            }
            return ResponseEntity.ok("Order successfully added.");
        } catch (Exception e) {
            // Fallback 발생 시 메시지 반환
            return ResponseEntity.status(500).body("잠시 후에 주문 추가를 요청 해주세요.");
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> addProductToOrder(
            @PathVariable Long orderId,
            @RequestBody Long productId
    ) {
        try {
            // 서비스 호출로 상품 추가 처리
            orderService.addProductToOrder(orderId, productId);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Server-Port", serverPort);

            return ResponseEntity.ok().headers(headers).body("Product successfully added to the order.");
        } catch (FeignException e) {
            // Feign Client 호출 중 발생한 예외 처리
            return ResponseEntity.status(404).body("Product service is unavailable or product does not exist.");
        } catch (IllegalArgumentException e) {
            // 상품이 없거나 유효하지 않을 때
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to add product to the order.");
        }
    }



    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        try {
            // Service에서 반환된 OrderDto를 바로 사용
            OrderResponseDto orderDto = orderService.getOrderById(orderId);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Server-Port", serverPort);

            return ResponseEntity.ok().headers(headers).body(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
