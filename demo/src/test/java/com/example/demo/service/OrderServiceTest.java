package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.cart.ShoppingCart;
import com.example.demo.model.order.Order;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        accountRepository.deleteAll();

        Account account = new Account();
        account.setLoginName("buyer");
        account.setPassword("secret");
        accountRepository.save(account);

        Product first = productRepository.save(createProduct("Phone", 100.0));
        Product second = productRepository.save(createProduct("Laptop", 300.0));

        cart = new ShoppingCart();
        cart.addProduct(first, 2);
        cart.addProduct(second, 1);
    }

    @Test
    void checkout_shouldCreateOrderWithDetailsAndTotalAmount() {
        Order order = orderService.checkout("buyer", cart);

        assertEquals("buyer", order.getAccount().getLoginName());
        assertEquals(2, order.getOrderDetails().size());
        assertEquals(500.0, order.getTotalAmount());
    }

    private Product createProduct(String name, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
