package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findProducts(String keyword, Long categoryId, String sortOption, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sortOption));
        return productRepository.findByFilters(normalizeKeyword(keyword), categoryId, pageable);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private Sort buildSort(String sortOption) {
        if ("price_asc".equalsIgnoreCase(sortOption)) {
            return Sort.by(Sort.Direction.ASC, "price");
        }

        if ("price_desc".equalsIgnoreCase(sortOption)) {
            return Sort.by(Sort.Direction.DESC, "price");
        }

        return Sort.by(Sort.Direction.ASC, "id");
    }

    private String normalizeKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }

        return keyword.trim();
    }
}
