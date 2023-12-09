package com.ecommerce.controller;

import com.ecommerce.model.Price;
import com.ecommerce.service.priceService.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/price")
public class PriceController {
    private final PriceService<Price> priceService;

    @Autowired
    public PriceController(PriceService<Price> priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/list")
    public List<Price> getAllPrices() {
        return priceService.list();
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createPrice(@RequestBody Price price) {
        priceService.create(price);
        return new ResponseEntity<>("New Price successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public Price getPriceByIOd(@PathVariable int id) {
        return priceService.get(id).orElse(null);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updatePrice(@RequestBody Price price, @PathVariable int id) {
        priceService.update(price, id);
        return new ResponseEntity<>("The price has been updated", HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deletePrice(@PathVariable int id) {
        priceService.delete(id);
        return new ResponseEntity<>("The price has been deleted", HttpStatus.OK);
    }
}