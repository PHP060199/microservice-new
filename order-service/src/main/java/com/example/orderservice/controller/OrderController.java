package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placedOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO);
        return "Order Placed Successfully" ;
    }

    @GetMapping("/getAllOrderById")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getAllOrderById(@RequestParam Long orderId) {
        return orderService.getOrderDTOById(orderId);
    }

    @GetMapping("/getAllOrder")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrder() {
        return orderService.getAllOrder();
    }
}
