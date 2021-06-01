package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface IProductService {
    Product createProduct(Product product);
    boolean updateProduct(Product product);
    List<Product> getAllProducts();
}
