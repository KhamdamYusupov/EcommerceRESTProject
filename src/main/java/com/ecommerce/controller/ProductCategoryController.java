package com.ecommerce.controller;

import com.ecommerce.model.ProductCategory;
import com.ecommerce.service.productCategoryService.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("productCategory")
public class ProductCategoryController {
    private final ProductCategoryService<ProductCategory> productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService<ProductCategory> productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/list")
    public List<ProductCategory> getAllProductCategory() {
        return productCategoryService.list();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProductCategory(@RequestBody ProductCategory productCategory) {
        productCategoryService.create(productCategory);
        return new ResponseEntity<>("New product category created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable("id") int id) {
    ProductCategory productCategory = productCategoryService.get(id);
    if(productCategory!=null) {
        return new ResponseEntity<>(productCategory, HttpStatus.OK);
    }else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateProductCategory(@RequestBody ProductCategory productCategory, @PathVariable("id") int id) {
        productCategoryService.update(productCategory, id);
        return new ResponseEntity<>("The product category has been updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable("id") int id) {
        productCategoryService.delete(id);
        return new ResponseEntity<>("The productCategory has been deleted", HttpStatus.OK);
    }
}
