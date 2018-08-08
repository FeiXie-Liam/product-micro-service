package com.thoughtworks.product.service;

import com.thoughtworks.product.entity.Product;
import com.thoughtworks.product.exception.ProductNotFoundException;
import com.thoughtworks.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    public void update(Long id, Product product) {
        Product oldProduct = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setUnit(product.getUnit());
        oldProduct.setImageUrl(product.getImageUrl());
        productRepository.save(oldProduct);
    }
}
