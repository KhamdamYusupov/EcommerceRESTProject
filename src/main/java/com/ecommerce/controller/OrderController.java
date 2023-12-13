package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.orderService.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public List<Order> getAllOrders() {
        return orderService.list();
    }

    @PostMapping(value = "/create", consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.create(order);
        return new ResponseEntity<>("New order is successfully created", HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Order>getOrderById(@PathVariable("id") int id) {
        Order order = orderService.get(id);
        if(order!=null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateOrder(@RequestBody Order order, @PathVariable("id") int id) {
        orderService.update(order, id);
        return new ResponseEntity<>("The order with an id of " + id + " successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") int id) {
        orderService.delete(id);
        return new ResponseEntity<>("The order with an Id of " + id + " has been deleted", HttpStatus.OK);
    }
}
