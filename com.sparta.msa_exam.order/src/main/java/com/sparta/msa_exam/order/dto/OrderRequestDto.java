package com.sparta.msa_exam.order.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDto {
    private List<Long> productIds; // 요청으로 넘어오는 상품 ID 리스트
}
