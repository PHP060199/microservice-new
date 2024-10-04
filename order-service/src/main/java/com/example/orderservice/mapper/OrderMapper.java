package com.example.orderservice.mapper;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderDTO orderDTO);

    @Mapping(target = "orderLineItemsDTOList", ignore = true)
    OrderDTO toOrderDTO(Order order);
}
