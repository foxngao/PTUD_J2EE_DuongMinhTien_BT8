package com.example.demo.model.cart;

import com.example.demo.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class ShoppingCart {
    private final Map<Long, CartItem> itemMap = new LinkedHashMap<>();

    public void addProduct(Product product, int quantity) {
        CartItem existingItem = itemMap.get(product.getId());
        if (existingItem == null) {
            itemMap.put(product.getId(), new CartItem(product.getId(), product.getName(), product.getPrice(), quantity,
                    product.getImageFileName()));
            return;
        }

        existingItem.setQuantity(existingItem.getQuantity() + quantity);
    }

    public void updateQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            itemMap.remove(productId);
            return;
        }

        CartItem existingItem = itemMap.get(productId);
        if (existingItem != null) {
            existingItem.setQuantity(quantity);
        }
    }

    public void removeProduct(Long productId) {
        itemMap.remove(productId);
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(itemMap.values());
    }

    public int getTotalQuantity() {
        return itemMap.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public double getTotalAmount() {
        return itemMap.values().stream().mapToDouble(CartItem::getLineTotal).sum();
    }

    public boolean isEmpty() {
        return itemMap.isEmpty();
    }

    public void clear() {
        itemMap.clear();
    }
}
