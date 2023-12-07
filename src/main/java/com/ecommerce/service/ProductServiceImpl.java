package com.ecommerce.service;


import com.ecommerce.model.Prices;
import com.ecommerce.model.Product;
import com.ecommerce.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService<Product> {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final JdbcTemplate jdbcTemplate;

    RowMapper<Product>rowMapper = (rs, rowNum) -> {
      Product product = new Product();
      product.setProductId(rs.getInt("product_id"));
      product.setProductName(rs.getString("product_name"));
      product.setCategoryId(rs.getInt("category_id"));
      product.setExpirationDate(rs.getDate("expiration_date"));
      product.setCreatedDate(rs.getDate("created_date"));
      product.setUpdatedDate(rs.getDate("updated_date"));
      product.setCreatedBy(rs.getString("created_by"));
      product.setUpdatedBy(rs.getString("updated_by"));
      return product;
    };

    @Autowired
    public ProductServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> list() {
        String SQL_FIND_ALL = "select * from products";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(Product product) {
        String SQL_SAVE = "insert into products(product_id, product_name, category_id, expiration_date, created_date, updated_date, created_by, updated_by) values(?,?,?,?,?,?,?,?)";
        final int insertedRow = jdbcTemplate.update(SQL_SAVE, product.getProductId(), product.getProductName(), product.getCategoryId(), product.getExpirationDate(), product.getCreatedDate(), product.getUpdatedDate(), product.getCreatedBy(), product.getUpdatedBy());
        if(insertedRow == 1) {
            logger.info("New product created: " + product.getProductName());
        }
    }

    @Override
    public Optional<Product> get(int id) {
        String SQL_FIND_BY_ID = "select product_name, category_id, expiration_date, created_date, updated_date, created_by, updated_by from products where product_id=?";
        Product product = null;
        try{
            product = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, id);
        } catch (DataAccessException ex) {
            logger.info("Not found the product with an Id of " + id);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product, int id) {
        String SQL_UPDATE = "update products set product_name=?, category_id=?, expiration_date=?, created_date=?, updated_date=?, created_by=?, updated_by=? where product_id=?";
        final int updatedRow = jdbcTemplate.update(SQL_UPDATE, product.getProductId(), product.getProductName(), product.getCategoryId(), product.getExpirationDate(), product.getCreatedDate(), product.getUpdatedDate(), product.getCreatedBy(), product.getUpdatedBy(), id);
        if(updatedRow ==1) {
            logger.info("Product updated: " + product.getProductName());
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from products where product_id=?";
        final int deletedRow = jdbcTemplate.update(SQL_DELETE, id);
        if(deletedRow == 1) {
            logger.info("Product has been deleted: " + id);
        }
    }
}
