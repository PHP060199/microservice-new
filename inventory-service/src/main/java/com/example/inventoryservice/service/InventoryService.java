package com.example.inventoryservice.service;


import com.example.common.exception.CustomException;
import com.example.common.exception.define.ErrorCode;
import com.example.common.exception.define.ErrorMessage;
import com.example.inventoryservice.domain.Inventory;

import com.example.inventoryservice.dto.InventoryDTO;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService  {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public List<InventoryDTO> getInventoryStillInStock() {
        return inventoryRepository.findAllByQuantityGreaterThan(0)
                .stream().map(inventoryMapper::toInventoryDTO).toList();
    }


    public Integer getQuantityByCode(String code) {

        Inventory inventory = inventoryRepository.findByCode(code).orElse(null);
        if (inventory != null) {
            return inventory.getQuantity();
        }
        return 0;
    }


    @Transactional
    public void setQuantity(List<InventoryDTO> inventoryDTOS) {

        inventoryDTOS.forEach(inventory ->{
            Inventory inv = inventoryRepository.findByCode(inventory.getCode()).orElse(null);
            if (inv != null) {
                inv.setQuantity(inventory.getQuantity());
                inventoryRepository.save(inv);
            }
        });
    }


    public InventoryDTO create(InventoryDTO inventoryDTO) {
        if (inventoryRepository.existsByCode(inventoryDTO.getCode())) {
            throw new CustomException(ErrorMessage.CODE_ALREADY_EXISTS, ErrorCode.exist);
        }
        Inventory inventory = inventoryMapper.toInventory(inventoryDTO);
        return inventoryMapper.toInventoryDTO(inventoryRepository.save(inventory));
    }


    public InventoryDTO update(InventoryDTO inventoryDTO) {
       inventoryRepository.findById(inventoryDTO.getId())
               .orElseThrow(()-> new CustomException(ErrorMessage.INVENTORY_NOT_FOUND, ErrorCode.notFound));

       if (inventoryRepository.existsByCode(inventoryDTO.getCode())) {
           throw new CustomException(ErrorMessage.CODE_ALREADY_EXISTS, ErrorCode.exist);
       }
       Inventory inventory = inventoryMapper.toInventory(inventoryDTO);
       return inventoryMapper.toInventoryDTO(inventoryRepository.save(inventory));
    }

    public List<InventoryDTO> findAll() {
        return inventoryRepository.findAll().stream().map(inventoryMapper::toInventoryDTO).toList();
    }

    public void delete(Long id) {
        inventoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMessage.INVENTORY_NOT_FOUND, ErrorCode.notFound));
        inventoryRepository.deleteById(id);
    }

}
