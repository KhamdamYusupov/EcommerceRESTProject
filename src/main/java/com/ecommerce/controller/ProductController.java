package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import com.ecommerce.model.ProductView;
import com.ecommerce.service.productService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/list")
    public List<Product> getAllProducts(){
        return productService.list();
    }

    @GetMapping("/listForUI")
    public List<ProductView>getAllProductViews(){
        return productService.listForUI();
    }

    @PostMapping(value = "/create",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        productService.create(product);
        return new ResponseEntity<>("New Product successfully created", HttpStatus.CREATED);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id){
        Product product = productService.get(id);
        if(product!=null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/update/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String>updateProduct(@RequestBody Product product, @PathVariable("id") int id) {
           productService.update(product, id);
           return new ResponseEntity<>("The product with an Id of " + id + " has been UPDATED", HttpStatus.OK);
    }

    @DeleteMapping( "/delete/{id}")
    public ResponseEntity<String>deleteProduct(@PathVariable("id") int id){
        productService.delete(id);
        return new ResponseEntity<>("The product with an Id of " + id + " has been DELETED", HttpStatus.OK);
    }
}
