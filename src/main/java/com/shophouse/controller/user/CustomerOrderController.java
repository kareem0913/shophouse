package com.shophouse.controller.user;

import com.shophouse.model.dto.order.OrderCreate;
import com.shophouse.model.dto.order.OrderResponse;
import com.shophouse.security.UserPrincipal;
import com.shophouse.service.customer.order.CustoemrOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Customer Order", description = "Customer Order API")
public class CustomerOrderController {

    private final CustoemrOrderService customerOrderService;

    @PostMapping
    public OrderResponse httpCreateOrder(@NotNull @Valid
                                             @RequestBody final OrderCreate orderCreate,
                                         @AuthenticationPrincipal final UserPrincipal currentUser
                                         ) {
        return customerOrderService.placeOrder(orderCreate, currentUser.getId());
    }

    @GetMapping
    public Page<OrderResponse> httpGetUserOrders(@AuthenticationPrincipal final UserPrincipal currentUser,
                                                 Pageable pageable) {
        return customerOrderService.getUserOrders(currentUser.getId(), pageable);
    }

    @GetMapping("/{orderId}")
    public OrderResponse httpGetOrderById(@PathVariable Long orderId,
                                          @AuthenticationPrincipal final UserPrincipal currentUser) {
        return customerOrderService.getOrderById(orderId, currentUser.getId());
    }
}
