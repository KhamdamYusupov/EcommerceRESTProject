package com.ecommerce.service.productCategoryService;

import com.ecommerce.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService{
    List<ProductCategory>list();
    void create(ProductCategory productCategory);
    ProductCategory get(int id);
    void update(ProductCategory productCategory, int id);
    void delete(int id);
}
