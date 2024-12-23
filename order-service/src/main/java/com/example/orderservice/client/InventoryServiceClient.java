package com.example.orderservice.client;


import com.example.orderservice.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @GetMapping(value = {"/api/inventory/getInventoryStillInStock"})
    List<InventoryDTO> getInventoryStillInStock();

    @PutMapping("/api/inventory/setQuantity")
    void setQuantity(@RequestBody List<InventoryDTO> inventoryDTOList);

    @GetMapping("/api/inventory/getQuantityByCode")
    Integer getQuantityByCode(@RequestParam("code") String code);
}
