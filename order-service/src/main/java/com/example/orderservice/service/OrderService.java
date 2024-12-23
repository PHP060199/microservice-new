package com.example.orderservice.service;


import com.example.orderservice.client.InventoryServiceClient;
import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.InventoryDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderLineItemsDTO;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.exception.define.ErrorCode;
import com.example.orderservice.exception.define.ErrorMessage;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsService orderLineItemsService;
    private final InventoryServiceClient inventoryServiceClient;
    private final OrderMapper orderMapper;

    public void placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItemsDTO> orderLineItemsDTOList = orderDTO.getOrderLineItemsDTOList();

        //Stock
        //Get list inventory if quantity > 0
        List<InventoryDTO> inventoryStillInStock = inventoryServiceClient.getInventoryStillInStock();
        List<String> codes = inventoryStillInStock.stream().map(InventoryDTO::getCode).toList();

        //List update quantity of inventory
        List<InventoryDTO> updateList = new ArrayList<>();

        orderLineItemsDTOList.forEach(orderLineItemsDTO -> {

            if (!codes.contains(orderLineItemsDTO.getCode())) {
                throw new CustomException(
                        String.format(ErrorMessage.PRODUCT_NOT_FOUND_IN_STOCK, orderLineItemsDTO.getCode()),
                        ErrorCode.notFound);
            }
            Integer orderQuantity = orderLineItemsDTO.getQuantity();
            Integer stockQuantity = inventoryServiceClient.getQuantityByCode(orderLineItemsDTO.getCode());

            if (stockQuantity < orderQuantity) {
                throw new CustomException(
                        String.format(ErrorMessage.PRODUCT_NOT_ENOUGH_QUANTITY, orderLineItemsDTO.getCode()),
                        ErrorCode.mismatch);
            }

            InventoryDTO inventoryDTO = new InventoryDTO();
            inventoryDTO.setCode(orderLineItemsDTO.getCode());
            inventoryDTO.setQuantity(stockQuantity - orderQuantity);
            updateList.add(inventoryDTO);
        });

        //Reset quantity
        inventoryServiceClient.setQuantity(updateList);

        //Save order
        Order finalOrder = orderRepository.save(order);
        orderLineItemsDTOList.forEach(orderLineItemsDTO ->
                orderLineItemsService.placeOrderLineItemsService(orderLineItemsDTO, finalOrder.getId()));
    }

    public OrderDTO getOrderDTOById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.ORDER_NOT_FOUND, ErrorCode.notFound));


        OrderDTO orderDTO = orderMapper.toOrderDTO(order);
        orderDTO.setOrderLineItemsDTOList(orderLineItemsService.getOrderLineItemsDTO(order.getId()));
        return orderDTO;
    }

    public List<OrderDTO> getAllOrder() {
        List<OrderDTO> orderDTOList = orderRepository.findAll().stream().map(orderMapper::toOrderDTO).toList();
        orderDTOList.forEach(orderDTO -> {
            orderDTO.setOrderLineItemsDTOList(orderLineItemsService.getOrderLineItemsDTO(orderDTO.getId()));
        });
        return orderDTOList;
    }
}
