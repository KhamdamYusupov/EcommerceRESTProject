package com.ecommerce.service.userService;

import com.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> list();
    void create(User user);
    User get(int id);
    void update(User user, int id);
    void delete(int id);
}
