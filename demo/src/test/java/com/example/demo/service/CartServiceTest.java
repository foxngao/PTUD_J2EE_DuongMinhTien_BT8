package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.cart.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartServiceTest {

    private final CartService cartService = new CartService();

    @Test
    void addAndUpdateCart_shouldKeepQuantityAndTotalAmount() {
        MockHttpSession session = new MockHttpSession();
        Product phone = createProduct(1L, "Phone", 100.0);

        cartService.addToCart(phone, 2, session);
        cartService.addToCart(phone, 1, session);
        cartService.updateQuantity(1L, 5, session);

        ShoppingCart cart = cartService.getCart(session);

        assertEquals(5, cart.getTotalQuantity());
        assertEquals(500.0, cart.getTotalAmount());
        assertEquals(1, cart.getItems().size());
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    @Test
    void removeFromCart_shouldDeleteItemFromSessionCart() {
        MockHttpSession session = new MockHttpSession();
        Product phone = createProduct(1L, "Phone", 100.0);

        cartService.addToCart(phone, 2, session);
        cartService.removeFromCart(1L, session);

        ShoppingCart cart = cartService.getCart(session);

        assertEquals(0, cart.getTotalQuantity());
        assertEquals(0.0, cart.getTotalAmount());
        assertEquals(0, cart.getItems().size());
    }

    private Product createProduct(Long id, String name, Double price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
