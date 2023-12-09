package com.ecommerce.service.productCategoryService;

import com.ecommerce.model.ProductCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class ProductCategoryServiceImpl implements ProductCategoryService<ProductCategory> {
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    RowMapper<ProductCategory> rowMapper = (rs, rowNum) -> {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(rs.getInt("id"));
        productCategory.setName(rs.getString("name"));
        productCategory.setCreatedDate(Instant.parse(rs.getString("created_at")));
        productCategory.setUpdatedDate(Instant.parse(rs.getString("updated_at")));
        productCategory.setCreatedBy(rs.getString("created_by"));
        productCategory.setUpdatedBy(rs.getString("updated_by"));
        return productCategory;
    };

    @Override
    public List<ProductCategory> list() {
        String SQL_LIST = "select * from product_categories";
        return jdbcTemplate.query(SQL_LIST, rowMapper);
    }

    @Override
    public void create(ProductCategory productCategory) {
        String SQL_INSERT_INTO = "insert into product_categories(id, name, created_at, updated_at, created_by, updated_by) values(?,?,?,?,?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSERT_INTO, productCategory.getId(), productCategory.getName(), productCategory.getCreatedDate(), productCategory.getUpdatedDate(), productCategory.getCreatedBy(), productCategory.getUpdatedBy());
        if (inserted == 1) {
            logger.info("New productCategory is inserted with and Id of " + productCategory.getId());
        }

    }

    @Override
    public Optional<ProductCategory> get(int id) {
        String SQL_GET_BY_ID = "select name, created_at, updated_at, created_by, updated_by from product_categories where id=?";
        ProductCategory productCategory = null;
        try {
            productCategory = jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id);
        } catch (DataAccessException e) {
            logger.info("Could not found productCategory with an Id of " + id);
        }
        return Optional.ofNullable(productCategory);
    }

    @Override
    public void update(ProductCategory productCategory, int id) {
        String SQL_UPDATE = "update product_categories set name=?, created_at=?, updated_at=?, created_by=?, updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_UPDATE, productCategory.getName(), productCategory.getCreatedDate(), productCategory.getUpdatedDate(), productCategory.getCreatedBy(), productCategory.getUpdatedBy(), id);
        if (updated == 1) {
            logger.info("Updated productCategory with an Id of " + id);
        }

    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from product_categories where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETE, id);
        if (deleted == 1) {
            logger.info("Deleted productCategory with an Id of " + id);
        }
    }
}
