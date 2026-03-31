package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.cart.CartItem;
import com.example.demo.model.cart.ShoppingCart;
import com.example.demo.model.order.Order;
import com.example.demo.model.order.OrderDetail;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order checkout(String loginName, ShoppingCart cart) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Account account = accountRepository.findByLoginName(loginName)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + loginName));

        Order order = new Order();
        order.setAccount(account);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CREATED");

        double totalAmount = 0;

        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setUnitPrice(product.getPrice());
            orderDetail.setLineTotal(product.getPrice() * item.getQuantity());
            order.addOrderDetail(orderDetail);
            totalAmount += orderDetail.getLineTotal();
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public Order findOrderForUser(Long orderId, String loginName) {
        return orderRepository.findByIdAndAccount_LoginName(orderId, loginName)
                .orElse(null);
    }
}
