package com.example.orderservice.service;

import com.example.orderservice.domain.OrderLineItems;
import com.example.orderservice.dto.OrderLineItemsDTO;
import com.example.orderservice.mapper.OrderLineItemsMapper;
import com.example.orderservice.repository.OrderLineItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineItemsService {

    private final OrderLineItemsRepository orderLineItemsRepository;

    private final OrderLineItemsMapper orderLineItemsMapper;

    public void placeOrderLineItemsService(OrderLineItemsDTO orderLineItemsDTO, Long orderId) {
        OrderLineItems orderLineItems = orderLineItemsMapper.toOrderLineItems(orderLineItemsDTO);
        orderLineItems.setOrderId(orderId);
        orderLineItemsRepository.save(orderLineItems);
    }

    public List<OrderLineItemsDTO> getOrderLineItemsDTO(Long orderId) {
        return orderLineItemsRepository.findAllByOrderId(orderId)
                .stream().map(orderLineItemsMapper::toOrderLineItemsDTO).toList();
    }
}
