package com.ecommerce.service.productService;


import com.ecommerce.model.Product;
import com.ecommerce.model.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ProductServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
      RowMapper<Product>rowMapper = (rs, rowNum) -> {
      Product product = new Product();
      product.setId(rs.getInt("id"));
      product.setName(rs.getString("name"));
      product.setCategoryId(rs.getInt("category_id"));
          if(rs.getTimestamp("expiration_date")!=null){
              product.setExpirationDate(rs.getTimestamp("expiration_date").toInstant());
          }
          if(rs.getTimestamp("created_at")!=null){
              product.setCreatedDate(rs.getTimestamp("created_at").toInstant());
          }
          if(rs.getTimestamp("updated_at")!=null) {
              product.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
          }
      product.setCreatedBy(rs.getString("created_by"));
      product.setUpdatedBy(rs.getString("updated_by"));
      return product;
    };

    RowMapper<ProductView> productViewRowMapper = (rs, rowNum) -> {
        ProductView productView = new ProductView();
        productView.setId(rs.getInt("id"));
        productView.setName(rs.getString("name"));
        productView.setCategoryId(rs.getInt("category_id"));
        productView.setCategoryName(rs.getString("category_name"));
        productView.setPriceValue(rs.getInt("value"));
        productView.setCurrency(rs.getString("currency"));
        productView.setPriceType(rs.getString("price_type_name"));
        if(rs.getTimestamp("expiration_date")!=null){
            productView.setExpirationDate(rs.getTimestamp("expiration_date").toInstant());
        }
        if(rs.getTimestamp("created_at")!=null){
            productView.setCreatedDate(rs.getTimestamp("created_at").toInstant());
        }
        if(rs.getTimestamp("updated_at")!=null) {
            productView.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
        }
        productView.setCreatedBy(rs.getString("created_by"));
        productView.setUpdatedBy(rs.getString("updated_by"));
        return productView;
    };


    @Override
    public List<Product> list() {
        String SQL_FIND_ALL = "select * from products";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }
    @Override
    public List<ProductView> listForUI() {
        String SQL_FIND_LIST_FOR_UI = """
                select p.id, p.name, p.category_id, pc.name as category_name,
                pr.value, pr.currency, pt.name as price_type_name, p.expiration_date, p.created_at, p.updated_at, p.created_by, p.updated_by
                from products p
                left join product_category pc
                on p.category_id=pc.id
                left join prices pr
                on p.id=pr.product_id
                left join price_types pt
                on pr.type_id = pt.id
                """;
        return jdbcTemplate.query(SQL_FIND_LIST_FOR_UI, productViewRowMapper);
    }

    @Override
    public void create(Product product) {
        String SQL_SAVE = "insert into products(name, category_id, expiration_date, created_by) values(?,?,?,?)";
        final int insertedRow = jdbcTemplate.update(SQL_SAVE, product.getName(), product.getCategoryId(), Timestamp.from(product.getExpirationDate()), product.getCreatedBy());
        if(insertedRow == 1) {
            logger.info("New product created: " + product.getName());
        }
    }
    @Override
    public Product get(int id) {
        String SQL_FIND_BY_ID = "select id, name, category_id, expiration_date, created_at, updated_at, created_by, updated_by from products where id=?";
        Optional<Product>product = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, id));
        return product.orElse(null);
    }

    @Override
    public void update(Product product, int id) {
        String SQL_UPDATE = "update products set name=?, category_id=?, expiration_date=?, updated_at=current_timestamp, updated_by=? where id=?";
        final int updatedRow = jdbcTemplate.update(SQL_UPDATE, product.getName(), product.getCategoryId(), Timestamp.from(product.getExpirationDate()), product.getUpdatedBy(), id);
        if(updatedRow==1) {
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
