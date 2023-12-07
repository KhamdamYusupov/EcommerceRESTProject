package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostConstruct
    public void test() {
        System.out.println("Controller has been created.");
    }

    private final ProductService<Product> productService;

    @Autowired
    public ProductController(ProductService<Product> productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public List<Product> getAllProducts(){
        return productService.list();
    }


    @PostMapping(path = "/create",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        productService.create(product);
        return new ResponseEntity<>("New Product successfully created", HttpStatus.CREATED);

    }

    @GetMapping("get/{id}")
    public Product getProductById(@PathVariable int id){
     return productService.get(id).orElse(null);
    }


    @PutMapping(path = "/update/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String>updateProduct(@RequestBody Product product, @PathVariable int id) {
           productService.update(product, id);
           return new ResponseEntity<>("The product with an Id of " + id + " has been UPDATED", HttpStatus.OK);
    }

    @PostMapping(path = "delete/{id}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String>deleteProduct(@PathVariable int id){
        productService.delete(id);
        return new ResponseEntity<>("The product with an Id of " + id + " has been DELETED", HttpStatus.OK);
    }
}
