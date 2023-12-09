package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.service.cartService.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService<Cart> cartCartService;

    public CartController(CartService<Cart> cartCartService) {
        this.cartCartService = cartCartService;
    }

    @GetMapping("/list")
    public List<Cart> getAllCarts() {
        return cartCartService.list();
    }

    @PostMapping(value = "/create", consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> createCart(@RequestBody Cart cart) {
        cartCartService.create(cart);
        return new ResponseEntity<>("New cart is successfully created", HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public Cart getCartById(@PathVariable int id) {
        return cartCartService.get(id).orElse(null);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateCart(@RequestBody Cart cart, @PathVariable int id) {
        cartCartService.update(cart, id);
        return new ResponseEntity<>("The cart with an id of " + id + " successfully updated", HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable int id) {
        cartCartService.delete(id);
        return new ResponseEntity<>("The cart with an Id of " + id + " has been deleted", HttpStatus.OK);
    }
}
