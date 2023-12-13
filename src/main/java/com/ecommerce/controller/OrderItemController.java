package com.ecommerce.controller;

import com.ecommerce.model.OrderItem;
import com.ecommerce.model.OrderItemView;
import com.ecommerce.service.orderItemService.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;
    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
    @GetMapping("/list")
    public List<OrderItem>getOrderItemList(){
        return orderItemService.list();
    }

    @GetMapping("/listForUI")
    public List<OrderItemView>getOrderItemViewList(){
        return orderItemService.listForUI();
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createOrderItem(@RequestBody OrderItem orderItem) {
        orderItemService.create(orderItem);
        return new ResponseEntity<>("New orderItem successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<com.ecommerce.model.OrderItem> getOrderItemById(@PathVariable("id") int id) {
        OrderItem orderItem = orderItemService.get(id);
        if(orderItem!=null) {
            return new ResponseEntity<>(orderItem, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateOrderItem(@RequestBody OrderItem orderItem, @PathVariable("id") int id) {
        orderItemService.update(orderItem, id);
        return new ResponseEntity<>("The orderItem has been updated", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>deleteOrderItem(@PathVariable("id") int id) {
        orderItemService.delete(id);
        return new ResponseEntity<>("The orderItem has been deleted", HttpStatus.OK);
    }
}
