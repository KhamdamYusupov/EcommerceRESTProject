package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService<User> userService;

    @Autowired
    public UserController(UserService<User> userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> getUserList() {
        return userService.list();
    }

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createUser(User user) {
        userService.create(user);
        return new ResponseEntity<>("New User has been created", HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.get(id).orElse(null);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable int id) {
        userService.update(user, id);
        return new ResponseEntity<>("The user has been updated", HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>("The user has been deleted", HttpStatus.OK);
    }
}
