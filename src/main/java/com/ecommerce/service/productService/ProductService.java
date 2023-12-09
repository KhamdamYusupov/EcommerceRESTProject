package com.ecommerce.service.productService;

import java.util.List;
import java.util.Optional;

public interface ProductService <Product>{
    List<Product> list();
    void create(Product product);
    Optional<Product> get(int id);
    void update(Product product, int id);
    void delete(int id);
}
