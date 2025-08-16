package com.shophouse.service.customer.order;

import com.shophouse.model.dto.order.OrderCreate;
import com.shophouse.model.dto.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustoemrOrderService {

    OrderResponse placeOrder(OrderCreate orderCreate, Long userId);

    Page<OrderResponse> getUserOrders(Long userId, Pageable pageable);

    OrderResponse getOrderById(Long orderId, Long userId);
}
