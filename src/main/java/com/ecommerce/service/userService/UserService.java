package com.ecommerce.service.userService;

import java.util.List;
import java.util.Optional;

public interface UserService<User> {
    List<User> list();
    void create(User user);
    Optional<User> get(int id);
    void update(User user, int id);
    void delete(int id);
}
