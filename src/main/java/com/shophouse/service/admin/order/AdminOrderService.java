package com.shophouse.service.admin.order;

import com.shophouse.model.dto.order.OrderResponse;
import com.shophouse.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {

    Page<OrderResponse> getAllOrders(Pageable pageable);

    OrderResponse getOrder(Long orderId);

    void changeOrderStatus(Long orderId, OrderStatus orderStatus);

}
