package com.example.ecommerce.service.bean;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.resource.IProductRepository;
import com.example.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Override
    public Product createProduct(Product product) {
        if(product != null){
            return repository.save(product);
        }
        return null;
    }

    @Override
    public boolean updateProduct(Product product) {
        if(product != null){
            repository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    public Optional<Product> getProduct(Long id) {
        return repository.findById(id);
    }
}
