package com.example.inventoryservice.service.implement;


import com.example.inventoryservice.domain.Inventory;
import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.exception.define.ErrorCode;
import com.example.inventoryservice.exception.define.ErrorMessage;
import com.example.inventoryservice.repository.InventoryRepository;
import com.example.inventoryservice.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Override
    public List<Inventory> codesListInStocks() {
        return inventoryRepository.findAllByQuantityGreaterThan(0);
    }

    @Override
    public Integer getQuantityCode(String code) {
        Inventory inventory = inventoryRepository.findByCode(code).orElse(null);
        if (inventory != null) {
            return inventory.getQuantity();
        }
        return 0;
    }

    @Override
    @Transactional
    public void setQuantity(List<Inventory> inventoryList) {

        inventoryList.forEach(inventory ->{
            Inventory inv = inventoryRepository.findByCode(inventory.getCode()).orElse(null);
            if (inv != null) {
                inv.setQuantity(inventory.getQuantity());
                inventoryRepository.save(inv);
            }
        });
    }

    @Override
    public Inventory addInventory(Inventory inventory) {
        Inventory inv = inventoryRepository.findByCode(inventory.getCode()).orElse(null);
        if (inv != null) {
            throw new CustomException(ErrorMessage.CODE_IS_EXISTS, ErrorCode.exist);
        }
        Inventory newInventory = new Inventory();
        newInventory.setQuantity(inventory.getQuantity());
        newInventory.setCode(inventory.getCode());
        newInventory =inventoryRepository.save(newInventory);
        return newInventory;
    }

    @Override
    public Inventory updateQuantity(Long id, Integer quantity) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.INVENTORY_NOT_FOUND ,ErrorCode.notFound));
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
        return inventory;
    }
}
