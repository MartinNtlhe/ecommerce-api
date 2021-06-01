package com.example.ecommerce.resource;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.service.bean.ProductServiceImpl;
import com.example.ecommerce.service.bean.UserTypeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.ecommerce.util.DateUtil.stringToDate;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private IProductRepository iProductRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    public void shouldCreateNewProduct() {
        final Product inputProduct = buildProduct("12343", "blwablwa", "Blaw blaw", 500.00);
        final Product expectedProduct = Mockito.mock(Product.class);

        when(iProductRepository.save(inputProduct)).thenReturn(expectedProduct);
        final Product actualPerson = productServiceImpl.createProduct(inputProduct);

        verify(iProductRepository, times(1)).save(inputProduct);
        assertEquals(expectedProduct, actualPerson);
    }

    private Product buildProduct(final String code, final String name, final String description, final Double price) {
        final Product product = new Product();
        product.setCode(code);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    @Test
    public void shouldGetProductById() {
        final Product inputProduct = buildProduct("12343", "blwablwa", "Blaw blaw", 800.00);

        when (iProductRepository.findById(inputProduct.getId())).thenReturn(java.util.Optional.of(inputProduct));
        final Product actualProduct = productServiceImpl.getProduct(inputProduct.getId()).get();

        verify(iProductRepository, times(1)).findById(inputProduct.getId());
        assertEquals(inputProduct, actualProduct);
    }

    @Test
    public void shouldRetrieveAllProducts(){

        final List<Product> expectedProducts = new ArrayList<Product>() {{
            add(buildProduct("12343", "blwablwa", "Blaw blaw", 800.00));
            add(buildProduct("65656", "tetete", "tetete tete", 566.00));
            add(buildProduct("434343", "trer", "gdf fdfg", 2300.00));
            add(buildProduct("4343", "dgdg", "jkjh eer", 900.00));
        }};

        when(iProductRepository.findAll()).thenReturn(expectedProducts);
        final List<Product> actualProducts = productServiceImpl.getAllProducts();

        verify(iProductRepository, times(1)).findAll();
        assertEquals(expectedProducts, actualProducts);
    }

}
