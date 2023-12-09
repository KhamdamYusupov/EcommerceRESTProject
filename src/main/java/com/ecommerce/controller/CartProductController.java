package com.ecommerce.controller;

import com.ecommerce.model.CartProduct;
import com.ecommerce.service.cartProductService.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartProduct")
public class CartProductController {
    private final CartProductService<CartProduct> cartProductService;

    @Autowired
    public CartProductController(CartProductService<CartProduct> cartProductService) {
        this.cartProductService = cartProductService;
    }


    @GetMapping("/list")
    public List<CartProduct>getCartProductList(){
        return cartProductService.list();
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createCartProduct(@RequestBody CartProduct cartProduct) {
        cartProductService.create(cartProduct);
        return new ResponseEntity<>("New cartProduct successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public CartProduct getCartProductById(@PathVariable int id) {
        return cartProductService.get(id).orElse(null);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateCartProduct(@RequestBody CartProduct cartProduct, @PathVariable int id) {
        cartProductService.update(cartProduct, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String>deleteCartProduct(@PathVariable int id) {
        cartProductService.delete(id);
        return new ResponseEntity<>("The cartProduct has been deleted", HttpStatus.OK);
    }

}
