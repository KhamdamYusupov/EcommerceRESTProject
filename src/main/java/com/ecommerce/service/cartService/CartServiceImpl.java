package com.ecommerce.service.cartService;

import com.ecommerce.model.Cart;
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
public class CartServiceImpl implements CartService<Cart> {
    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Cart> rowMapper = (rs, rowNum) -> {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setUserId(rs.getInt("user_id"));
        if(rs.getTimestamp("created_at")!=null){
            cart.setCreatedDate(rs.getTimestamp("created_at").toInstant());
        }
        if(rs.getTimestamp("updated_at") !=null) {
            cart.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
        }
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
        String SQL_INSERT_INTO = "insert into cart(user_id, created_by) values(?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSERT_INTO, cart.getUserId(), cart.getCreatedBy());
        if (inserted == 1) {
            logger.info("new cart inserted: " + cart.getId());
        }
    }

    @Override
    public Cart get(int id) {
        String SQL_FIND_BY_ID = "select id, user_id, created_at, updated_at, created_by, updated_by from cart where id=?";
        Optional<Cart> cart = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, id));
        return cart.orElse(null);
    }

    @Override
    public void update(Cart cart, int id) {
        String SQL_INSERT_INTO = "update cart set updated_at=current_timestamp, updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_INSERT_INTO, cart.getUpdatedBy(), id);
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
