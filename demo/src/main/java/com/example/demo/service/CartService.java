package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.cart.ShoppingCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    public static final String CART_SESSION_KEY = "shoppingCart";

    public ShoppingCart getCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }

        return cart;
    }

    public void addToCart(Product product, int quantity, HttpSession session) {
        validateQuantity(quantity);
        ShoppingCart cart = getCart(session);
        cart.addProduct(product, quantity);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(Long productId, int quantity, HttpSession session) {
        ShoppingCart cart = getCart(session);
        cart.updateQuantity(productId, quantity);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeFromCart(Long productId, HttpSession session) {
        ShoppingCart cart = getCart(session);
        cart.removeProduct(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void clearCart(HttpSession session) {
        ShoppingCart cart = getCart(session);
        cart.clear();
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}
