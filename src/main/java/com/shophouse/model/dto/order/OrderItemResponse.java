package com.shophouse.model.dto.order;

import com.shophouse.model.dto.product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double priceAtTime;
    private ProductResponse product;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
