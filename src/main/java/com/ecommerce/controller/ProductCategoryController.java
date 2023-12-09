package com.ecommerce.controller;

import com.ecommerce.model.ProductCategory;
import com.ecommerce.service.productCategoryService.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ProductCategory getProductCategoryById(@PathVariable int id) {
        return productCategoryService.get(id).orElse(null);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateProductCategory(@RequestBody ProductCategory productCategory, @PathVariable int id) {
        productCategoryService.update(productCategory, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable int id) {
        productCategoryService.delete(id);
        return new ResponseEntity<>("The productCategory has been deleted", HttpStatus.OK);
    }
}
