package com.ecommerce.service.cartService;

import com.ecommerce.model.Cart;
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
public class CartServiceImpl implements CartService<Cart> {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    RowMapper<Cart> rowMapper = (rs, rowNum) -> {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedDate(Instant.parse(rs.getString("created_at")));
        cart.setUpdatedDate(Instant.parse(rs.getString("updated_at")));
        cart.setCreatedBy(rs.getString("created_by"));
        cart.setUpdatedBy(rs.getString("updated_by"));
        return cart;
    };

    @Override
    public List<Cart> list() {
        String SQL_FIND_ALL = "select * from cart";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(Cart cart) {
        String SQL_INSERT_INTO = "insert into cart(id, user_id, created_at, updated_at, created_by, updated_by) values(?,?,?,?,?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSERT_INTO, cart.getId(), cart.getUserId(), cart.getCreatedDate(), cart.getUpdatedDate(), cart.getCreatedBy(), cart.getUpdatedBy());
        if (inserted == 1) {
            logger.info("new cart inserted: " + cart.getId());
        }
    }

    @Override
    public Optional<Cart> get(int id) {
        String SQL_FIND_BY_ID = "select user_id, created_at, updated_at, created_by, updated_by from cart where id=?";
        Cart cart = null;
        try {
            cart = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, id);
        } catch (DataAccessException e) {
            logger.info("could not find the cart with an id of " + id);
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public void update(Cart cart, int id) {
        String SQL_INSERT_INTO = "update cart set user_id=?, created_at=?, updated_at=?, created_by=?, updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_INSERT_INTO, cart.getUserId(), cart.getCreatedDate(), cart.getUpdatedDate(), cart.getUpdatedDate(), cart.getUpdatedBy(), id);
        if (updated == 1) {
            logger.info("The cart updated with an id of " + id);
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from cart where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETE, id);
        if (deleted == 1) {
            logger.info("The cart deleted with an id of " + id);
        }
    }
}
