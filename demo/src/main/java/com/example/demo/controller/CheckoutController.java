package com.example.demo.controller;

import com.example.demo.model.cart.ShoppingCart;
import com.example.demo.model.order.Order;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping
    public String checkout(Authentication authentication, HttpSession session) {
        ShoppingCart cart = cartService.getCart(session);
        Order order = orderService.checkout(authentication.getName(), cart);
        cartService.clearCart(session);
        return "redirect:/checkout/success?orderId=" + order.getId();
    }

    @GetMapping("/success")
    public String success(@RequestParam Long orderId, Authentication authentication, Model model) {
        Order order = orderService.findOrderForUser(orderId, authentication.getName());
        if (order == null) {
            return "redirect:/products";
        }

        model.addAttribute("orderId", orderId);
        return "order/success";
    }
}
