package com.ecommerce.service.orderService;

import com.ecommerce.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Order> rowMapper = (rs, rowNum) -> {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        if (rs.getTimestamp("created_at") != null) {
            order.setCreatedDate(rs.getTimestamp("created_at").toInstant());
        }
        if (rs.getTimestamp("updated_at") != null) {
            order.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
        }
        order.setCreatedBy(rs.getString("created_by"));
        order.setUpdatedBy(rs.getString("updated_by"));
        return order;
    };

    @Override
    public List<Order> list() {
        String SQL_FIND_ALL = "select * from orders";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(Order order) {
        String SQL_INSERT_INTO = "insert into orders(user_id, created_by) values(?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSERT_INTO, order.getUserId(), order.getCreatedBy());
        if (inserted == 1) {
            logger.info("new order inserted: " + order.getId());
        }
    }
    @Override
    public Order get(int id) {
        String SQL_FIND_BY_ID = "select id, user_id, created_at, updated_at, created_by, updated_by from orders where id=?";
        Optional<Order> order = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, id));
        return order.orElse(null);
    }

    @Override
    public void update(Order order, int id) {
        String SQL_INSERT_INTO = "update orders set user_id=?, updated_at=current_timestamp, updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_INSERT_INTO, order.getUserId(), order.getUpdatedBy(), id);
        if (updated == 1) {
            logger.info("The order updated with an id of " + id);
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from orders where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETE, id);
        if (deleted == 1) {
            logger.info("Deleted the order with an id of " + id);
        }
    }
}
