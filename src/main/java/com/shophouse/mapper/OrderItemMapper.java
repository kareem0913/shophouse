package com.shophouse.mapper;

import com.shophouse.model.dto.order.OrderItemResponse;
import com.shophouse.model.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public abstract class OrderItemMapper {

    public abstract OrderItemResponse toOrderItemResponse(OrderItem orderItem);

}
