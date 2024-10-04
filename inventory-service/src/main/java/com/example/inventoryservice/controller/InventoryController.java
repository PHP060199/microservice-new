package com.example.inventoryservice.controller;

import com.example.common.dto.respone.ApiResponse;
import com.example.inventoryservice.domain.Inventory;
import com.example.inventoryservice.dto.InventoryDTO;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/getQuantityByCode")
    public Integer getQuantityByCode(@RequestParam("code") String code){
        return inventoryService.getQuantityByCode(code);
    }

    @PutMapping("/setQuantity")
    public void setQuantity(@RequestBody List<InventoryDTO> inventoryDTOs){
        inventoryService.setQuantity(inventoryDTOs);
    }

    @GetMapping("/getInventoryStillInStock")
    public List<InventoryDTO> getInventoryStillInStock(){
        return inventoryService.getInventoryStillInStock();
    }

    @PostMapping()
    public ApiResponse<InventoryDTO> addInventory(@RequestBody InventoryDTO inventoryDTO) {
        return ApiResponse.<InventoryDTO>builder()
                .result(inventoryService.create(inventoryDTO))
                .build();
    }

    @PutMapping()
    public ApiResponse<InventoryDTO> updateQuantity(@RequestBody InventoryDTO inventoryDTO) {
        return ApiResponse.<InventoryDTO>builder()
                .result(inventoryService.update(inventoryDTO))
                .build();
    }
}
