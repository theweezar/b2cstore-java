package com.ecom.b2cstore.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecom.b2cstore.entity.Inventory;
import com.ecom.b2cstore.repository.InventoryRepository;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Collection<Inventory> getInventoriesByProductIds(Collection<String> productIds) {
        return inventoryRepository.findAllByProductIdIn(productIds);
    }

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepository.findByProduct_ProductId(productId).orElse(null);
    }

    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    public void updateInventory(Inventory inventory, int quantityChange) {
        if (inventory != null) {
            int newAts = inventory.getAts() + quantityChange;
            inventory.setAts(Math.max(newAts, 0));
            saveInventory(inventory);
        }
    }

    public void updateInventory(String productId, int quantityChange) {
        Inventory inventory = getInventoryByProductId(productId);
        updateInventory(inventory, quantityChange);
    }
}
