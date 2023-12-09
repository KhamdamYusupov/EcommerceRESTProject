package com.ecommerce.service.cartService;

import java.util.List;
import java.util.Optional;

public interface CartService <Cart>{
List<Cart>list();
void create(Cart cart);
Optional<Cart>get(int id);
void update(Cart cart, int id);
void delete(int id);
}
