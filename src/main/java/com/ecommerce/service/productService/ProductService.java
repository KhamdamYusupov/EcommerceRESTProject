package com.ecommerce.service.productService;

import com.ecommerce.model.Product;
import com.ecommerce.model.ProductView;

import java.util.List;
import java.util.Optional;

public interface ProductService{
    List<Product> list();

    List<ProductView> listForUI();

    void create(Product product);
    Product get(int id);
    void update(Product product, int id);
    void delete(int id);
}
