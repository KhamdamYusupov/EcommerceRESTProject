package com.ecommerce.service.productCategoryService;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService <ProductCategory>{
    List<ProductCategory>list();
    void create(ProductCategory productCategory);
    Optional<ProductCategory> get(int id);
    void update(ProductCategory productCategory, int id);
    void delete(int id);
}
