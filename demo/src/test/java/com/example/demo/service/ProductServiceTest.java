package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category phoneCategory;
    private Category laptopCategory;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        phoneCategory = categoryRepository.save(new Category(null, "Phone"));
        laptopCategory = categoryRepository.save(new Category(null, "Laptop"));

        saveProduct("Galaxy A", 300.0, phoneCategory);
        saveProduct("Galaxy S", 900.0, phoneCategory);
        saveProduct("iPhone", 1200.0, phoneCategory);
        saveProduct("ThinkPad", 1500.0, laptopCategory);
        saveProduct("MacBook", 2000.0, laptopCategory);
        saveProduct("Dell XPS", 1800.0, laptopCategory);
    }

    @Test
    void findProducts_shouldApplyKeywordCategoryAndSort() {
        Page<Product> result = productService.findProducts("galaxy", phoneCategory.getId(), "price_desc", 0, 5);

        assertEquals(2, result.getTotalElements());
        assertEquals("Galaxy S", result.getContent().get(0).getName());
        assertEquals("Galaxy A", result.getContent().get(1).getName());
    }

    @Test
    void findProducts_shouldReturnFiveProductsPerPage() {
        Page<Product> firstPage = productService.findProducts(null, null, null, 0, 5);
        Page<Product> secondPage = productService.findProducts(null, null, null, 1, 5);

        assertEquals(6, firstPage.getTotalElements());
        assertEquals(2, firstPage.getTotalPages());
        assertEquals(5, firstPage.getContent().size());
        assertEquals(1, secondPage.getContent().size());
    }

    private void saveProduct(String name, Double price, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        productRepository.save(product);
    }
}
