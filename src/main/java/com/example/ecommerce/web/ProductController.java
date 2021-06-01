package com.example.ecommerce.web;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "E-COMMERCE")
@RestController
@RequestMapping("${app.v1}/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @ApiOperation(value = "create a product", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Product is created successfully")})
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    @ResponseBody
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        if(product != null) {
            productService.createProduct(product);
            return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "update a product", response = ResponseEntity.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Product is updated successfully")})
    @RequestMapping(method = RequestMethod.PUT, value = "/update")
    @ResponseBody
    public ResponseEntity<String> updateProduct(@RequestBody Product body){
        if(productService.updateProduct(body)) {
            return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "View a list of Products", response = ResponseEntity.class)
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

}
