package com.ecommerce.controller;

import com.ecommerce.model.PriceType;
import com.ecommerce.service.priceTypeService.PriceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priceTypes")
public class PriceTypeController {
    private PriceTypeService<PriceType> priceTypeService;

    @Autowired
    public PriceTypeController(PriceTypeService<PriceType> priceTypeService) {
        this.priceTypeService = priceTypeService;
    }

    @GetMapping("/list")
    public List<PriceType> getAllPriceTypes() {
        return priceTypeService.list();
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createPriceType(@RequestBody PriceType priceType) {
        priceTypeService.create(priceType);
        return new ResponseEntity<>("New priseType has been created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public PriceType getPriceTypeById(@PathVariable int id) {
        return priceTypeService.get(id).orElse(null);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<String> updatePriceType(@RequestBody PriceType priceType, @PathVariable int id) {
        priceTypeService.update(priceType, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<String> deletePriceType(@PathVariable int id) {
        priceTypeService.delete(id);
        return new ResponseEntity<>("The priceType has been deleted", HttpStatus.OK);
    }

}
