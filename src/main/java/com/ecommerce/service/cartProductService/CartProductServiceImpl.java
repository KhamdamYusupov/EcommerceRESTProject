package com.ecommerce.service.cartProductService;

import com.ecommerce.model.CartProduct;
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
public class CartProductServiceImpl implements CartProductService<CartProduct>{
    private static final Logger logger = LoggerFactory.getLogger(CartProductServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartProductServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<CartProduct>rowMapper = (rs, rowNum) ->{
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(rs.getInt("id"));
        cartProduct.setProductId(rs.getInt("product_id"));
        cartProduct.setQuantity(rs.getInt("quantity"));
        if(rs.getTimestamp("created_at")!=null){
            cartProduct.setCreatedDate(rs.getTimestamp("created_at").toInstant());
        }
        if(rs.getTimestamp("updated_at") !=null) {
            cartProduct.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
        }
        cartProduct.setCreatedBy(rs.getString("created_by"));
        cartProduct.setUpdatedBy(rs.getString("updated_by"));
        return cartProduct;
    };

    @Override
    public List<CartProduct> list() {
        String SQL_FIND_ALL = "select * from cart_product";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(CartProduct cartProduct) {
        String SQL_CREATE = "insert into cart_product(product_id, quantity, created_by) values(?,?,?)";
        final int inserted = jdbcTemplate.update(SQL_CREATE, cartProduct.getProductId(), cartProduct.getQuantity(), cartProduct.getCreatedBy());
        if(inserted == 1) {
            logger.info("New cartProduct inserted: " + cartProduct.getId());
        }
    }

    @Override
    public CartProduct get(int id) {
        String SQL_GET_BY_ID = "select id, product_id, quantity, created_at, updated_at, created_by, updated_by from cart_product where id=?";
        Optional<CartProduct> cartProduct = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id));
      return cartProduct.orElse(null);
    }

    @Override
    public void update(CartProduct cartProduct, int id) {
        String SQL_UPDATE = "update cart_product set quantity=?, updated_at=current_timestamp, updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_UPDATE, cartProduct.getQuantity(), cartProduct.getUpdatedBy(), id);
        if(updated == 1) {
            logger.info("cartProduct updated: " + cartProduct.getId());
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from cart_product where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETE, id);
        if(deleted == 1){
            logger.info("deleted cartProduct with an id of " + id);
        }
    }
}
