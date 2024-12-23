package com.example.orderservice.controller;

import com.example.orderservice.dto.ApiResponse;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> placedOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO);
        return ApiResponse.<String>builder()
                .result("Order Placed Successfully")
                .build();
    }

    @GetMapping("/getAllOrderById")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<OrderDTO> getAllOrderById(@RequestParam Long orderId) {
        return ApiResponse.<OrderDTO>builder()
                .result(orderService.getOrderDTOById(orderId))
                .build();
    }

    @GetMapping("/getAllOrder")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<OrderDTO>> getAllOrder() {
        return ApiResponse.<List<OrderDTO>>builder()
                .result(orderService.getAllOrder())
                .build();
    }
}
