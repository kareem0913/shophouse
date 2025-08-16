package com.shophouse.model.dto.order;

import com.shophouse.model.enums.OrderStatsu;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreate {

    @NotNull(message = "Total amount is required")
    private Double totalAmount;

    @NotNull
    private Double totalDiscount;

    private OrderStatsu orderStatus = OrderStatsu.PENDING;

    @NotNull(message = "Order items cannot be null")
    @NotEmpty(message = "Order must have at least one item")
    @Size(max = 100, message = "Order cannot have more than 100 items")
    @Valid
    private List<OrderItemCreate> orderItems;

}