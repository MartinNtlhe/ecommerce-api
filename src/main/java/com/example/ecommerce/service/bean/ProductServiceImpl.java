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

    @Autowired
    public void createDefaultProduct() {
        final Product inputProduct = buildProduct("12343", "blwablwa", "Blaw blaw", 500.00);
        repository.save(inputProduct);
    }
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

    private Product buildProduct(final String code, final String name, final String description, final Double price) {
        final Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

}
