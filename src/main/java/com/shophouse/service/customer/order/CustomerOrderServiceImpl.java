package com.shophouse.service.customer.order;

import com.shophouse.error.exception.ResourceNotFoundException;
import com.shophouse.mapper.OrderMapper;
import com.shophouse.model.dto.order.OrderCreate;
import com.shophouse.model.dto.order.OrderItemCreate;
import com.shophouse.model.dto.order.OrderResponse;
import com.shophouse.model.entity.Order;
import com.shophouse.model.entity.OrderItem;
import com.shophouse.model.entity.Product;
import com.shophouse.model.entity.User;
import com.shophouse.repository.OrderRepository;
import com.shophouse.repository.ProductRepository;
import com.shophouse.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustoemrOrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse placeOrder(OrderCreate orderCreate, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" , "User not found with id: " + userId));

        Order order = orderMapper.toOrder(orderCreate);
        order.setUser(user);
        order.setOrderItems(new ArrayList<>());

        for ( OrderItemCreate orderItemCreate : orderCreate.getOrderItems()){
            Product product = productRepository.findById(orderItemCreate.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found" , "Product not found with id: " + orderItemCreate.getProductId()));
            OrderItem orderItem = OrderItem.builder()
                    .quantity(orderItemCreate.getQuantity())
                    .priceAtTime(product.getPrice())
                    .product(product)
                    .order(order)
                    .build();
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);
        log.info("Order placed successfully");
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> getUserOrders(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(orderMapper::toOrderResponse);
    }

    @Override
    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found", "Order not found with id: " + orderId));
        return orderMapper.toOrderResponse(order);
    }
}
