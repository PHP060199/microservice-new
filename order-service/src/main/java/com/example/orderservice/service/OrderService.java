package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    public void placeOrder(OrderDTO orderDTO);

    public OrderDTO getOrderDTOById(Long id);

    public List<OrderDTO> getAllOrder();
}
