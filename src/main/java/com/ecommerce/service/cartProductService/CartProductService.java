package com.ecommerce.service.cartProductService;

import com.ecommerce.model.CartProduct;

import java.util.List;
import java.util.Optional;

public interface CartProductService<CartProduct> {
    List<CartProduct> list();
    void create(CartProduct cartProduct);
    Optional<CartProduct> get(int id);
    void update(CartProduct cartProduct, int id);
    void delete(int id);
}
