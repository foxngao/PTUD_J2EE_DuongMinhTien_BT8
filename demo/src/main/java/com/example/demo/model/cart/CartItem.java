package com.example.demo.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productName;
    private Double price;
    private int quantity;
    private String imageFileName;

    public double getLineTotal() {
        return price * quantity;
    }
}
