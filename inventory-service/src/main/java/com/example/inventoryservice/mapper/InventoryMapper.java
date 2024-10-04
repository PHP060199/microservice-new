package com.example.inventoryservice.mapper;

import com.example.inventoryservice.domain.Inventory;
import com.example.inventoryservice.dto.InventoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory toInventory(InventoryDTO inventoryDTO);

    InventoryDTO toInventoryDTO(Inventory inventory);
}
