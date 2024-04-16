package com.example.orderservice.service.implement;

import com.example.inventoryservice.domain.Inventory;
import com.example.orderservice.client.InventoryServiceClient;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderLineItems;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.OrderLineItemsDTO;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.repository.OrderLineItemsRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.service.OrderLineItemsService;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsService orderLineItemsService;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final ModelMapper modelMapper;
    private final InventoryServiceClient inventoryServiceClient;
    @Override
    public void placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItemsDTO> orderLineItemsDTOList = orderDTO.getOrderLineItemsDTOList();

        //Stock
        //Get list inventory if quantity > 0
        List<Inventory> inventoryListValid = inventoryServiceClient.getCodesValid();
        List<String> codesValid = inventoryListValid.stream().map(Inventory::getCode).toList();

        //List update quantity of inventory
        List<Inventory> updateList = new ArrayList<>();

        orderLineItemsDTOList.forEach(orderLineItemsDTO -> {

            if (!codesValid.contains(orderLineItemsDTO.getCode())) {
                throw new IllegalArgumentException("Product with code: " + orderLineItemsDTO.getCode() + " is not in stock" );
            }
            Integer orderQuantity = orderLineItemsDTO.getQuantity();
            Integer stockQuantity = inventoryServiceClient.getQuantityByCode(orderLineItemsDTO.getCode());

            if (stockQuantity < orderQuantity) {
                throw new IllegalArgumentException("Product with code: " +orderLineItemsDTO.getCode() + " not enough quantity left. ");
            }

            Inventory inventory = new Inventory();
            inventory.setCode(orderLineItemsDTO.getCode());
            inventory.setQuantity(stockQuantity - orderQuantity);
            updateList.add(inventory);
        });

        //Reset quantity
        inventoryServiceClient.setQuantity(updateList);

        //Save order
        Order finalOrder = orderRepository.save(order);
        orderLineItemsDTOList.forEach(orderLineItemsDTO ->
                orderLineItemsService.placeOrderLineItemsService(orderLineItemsDTO, finalOrder.getId()));
    }

    @Override
    public OrderDTO getOrderDTOById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            List<OrderLineItems> orderLineItemsList = orderLineItemsRepository.findAllByOrderId(id);
            List<OrderLineItemsDTO> orderLineItemsDTOList = new ArrayList<>();
            orderLineItemsList.forEach(orderLineItems -> {
                orderLineItemsDTOList.add(modelMapper.map(orderLineItems, OrderLineItemsDTO.class));
            });
            orderDTO.setOrderLineItemsDTOList(orderLineItemsDTOList);
            return orderDTO;
        }
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.forEach(order -> {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            List<OrderLineItems> orderLineItemsList = orderLineItemsRepository.findAllByOrderId(order.getId());
            List<OrderLineItemsDTO> orderLineItemsDTOList = new ArrayList<>();
            orderLineItemsList.forEach(orderLineItems -> {
                orderLineItemsDTOList.add(modelMapper.map(orderLineItems, OrderLineItemsDTO.class));
            });
            orderDTO.setOrderLineItemsDTOList(orderLineItemsDTOList);
            orderDTOList.add(orderDTO);

        });
        return orderDTOList;
    }
}
