package com.shophouse.mapper;

import com.shophouse.model.dto.order.OrderCreate;
import com.shophouse.model.dto.order.OrderResponse;
import com.shophouse.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public abstract class OrderMapper {

    public abstract OrderResponse toOrderResponse(Order order);

    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract Order toOrder(OrderCreate orderCreate);

}
