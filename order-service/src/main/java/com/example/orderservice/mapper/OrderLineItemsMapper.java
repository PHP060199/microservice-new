package com.example.orderservice.mapper;

import com.example.orderservice.domain.OrderLineItems;
import com.example.orderservice.dto.OrderLineItemsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineItemsMapper {

    @Mapping(target = "orderId", ignore = true)
    OrderLineItems toOrderLineItems(OrderLineItemsDTO orderLineItemsDTO);

    OrderLineItemsDTO toOrderLineItemsDTO(OrderLineItems orderLineItems);
}
