package com.ecommerce.service.productService;


import com.ecommerce.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService<Product> {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final JdbcTemplate jdbcTemplate;

    RowMapper<Product>rowMapper = (rs, rowNum) -> {
      Product product = new Product();
      product.setId(rs.getInt("id"));
      product.setName(rs.getString("name"));
      product.setCategoryId(rs.getInt("category_id"));
      product.setPriceId(rs.getInt("price_id"));
      product.setExpirationDate(Instant.parse(rs.getString("expiration_date")));
      product.setCreatedDate(Instant.parse(rs.getString("created_at")));
      product.setUpdatedDate(Instant.parse(rs.getString("updated_at")));
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
        String SQL_SAVE = "insert into products(id, name, category_id, price_id, expiration_date, created_at, updated_at, created_by, updated_by) values(?,?,?,?,?,?,?,?,?)";
        final int insertedRow = jdbcTemplate.update(SQL_SAVE, product.getId(), product.getName(), product.getCategoryId(), product.getPriceId(), product.getExpirationDate(), product.getCreatedDate(), product.getUpdatedDate(), product.getCreatedBy(), product.getUpdatedBy());
        if(insertedRow == 1) {
            logger.info("New product created: " + product.getName());
        }
    }

    @Override
    public Optional<Product> get(int id) {
        String SQL_FIND_BY_ID = "select name, category_id, price_id, expiration_date, created_at, updated_at, created_by, updated_by from products where id=?";
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
        String SQL_UPDATE = "update products set name=?, category_id=?, price_id=?, expiration_date=?, created_at=?, updated_at=?, created_by=?, updated_by=? where id=?";
        final int updatedRow = jdbcTemplate.update(SQL_UPDATE, product.getName(), product.getCategoryId(), product.getPriceId(), product.getExpirationDate(), product.getCreatedDate(), product.getUpdatedDate(), product.getCreatedBy(), product.getUpdatedBy(), id);
        if(updatedRow ==1) {
            logger.info("Product updated: " + product.getName());
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from products where id=?";
        final int deletedRow = jdbcTemplate.update(SQL_DELETE, id);
        if(deletedRow == 1) {
            logger.info("Product has been deleted: " + id);
        }
    }
}
