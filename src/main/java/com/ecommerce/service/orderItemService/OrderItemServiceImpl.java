package com.ecommerce.service.orderItemService;

import com.ecommerce.model.OrderItem;
import com.ecommerce.model.OrderItemView;
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
public class OrderItemServiceImpl implements OrderItemService {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderItemServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<OrderItem>rowMapper = (rs, rowNum) ->{
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        if(rs.getTimestamp("created_at")!=null){
            orderItem.setCreatedDate(rs.getTimestamp("created_at").toInstant());
        }
        if(rs.getTimestamp("updated_at") !=null) {
            orderItem.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
        }
        orderItem.setCreatedBy(rs.getString("created_by"));
        orderItem.setUpdatedBy(rs.getString("updated_by"));
        orderItem.setPriceId(rs.getInt("price_id"));
        orderItem.setMeasurements(rs.getString("measurements"));
        orderItem.setOrderId(rs.getInt("order_id"));
        return orderItem;
    };

    RowMapper<OrderItemView>orderItemViewRowMapper = (rs, rowNum) ->{
        OrderItemView orderItemView = new OrderItemView();
        orderItemView.setId(rs.getInt("id"));
        orderItemView.setProductId(rs.getInt("product_id"));
        orderItemView.setQuantity(rs.getInt("quantity"));
        orderItemView.setUserId(rs.getInt("user_id"));
        orderItemView.setProductName(rs.getString("product_name"));
        orderItemView.setCategoryId(rs.getInt("category_id"));
        return orderItemView;
    };

    @Override
    public List<OrderItem> list() {
        String SQL_FIND_ALL = "select * from order_item";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public List<OrderItemView> listForUI() {
        String SQL_LIST_FOR_UI = """
                select oi.id, oi.product_id, oi.quantity, o.user_id, p.name as product_name, p.category_id
                from order_item oi
                left join orders o
                on oi.order_id=oi.id
                left join products p
                on oi.product_id=p.id
                """;
        return jdbcTemplate.query(SQL_LIST_FOR_UI, orderItemViewRowMapper);
    }

    @Override
    public void create(OrderItem orderItem) {
        String SQL_CREATE = "insert into order_item(product_id, quantity, created_by, price_id, measurements, order_id) values(?,?,?,?,?,?)";
        final int inserted = jdbcTemplate.update(SQL_CREATE, orderItem.getProductId(), orderItem.getQuantity(),
                orderItem.getCreatedBy(), orderItem.getPriceId(), orderItem.getMeasurements(), orderItem.getOrderId());
        if(inserted == 1) {
            logger.info("New orderItem inserted: " + orderItem.getId());
        }
    }

    @Override
    public OrderItem get(int id) {
        String SQL_GET_BY_ID = """
                select id, product_id, quantity, created_at, updated_at, created_by, updated_by,
                price_id, measurements, order_id from order_item where id=?
                """;
        Optional<OrderItem> orderItem = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id));
      return orderItem.orElse(null);
    }

    @Override
    public void update(OrderItem orderItem, int id) {
        String SQL_UPDATE = """
                                update order_item set product_id=?, quantity=?, updated_at=current_timestamp,
                                updated_by=?, price_id=?, measurements=?, order_id=? where id=?
                            """;
        final int updated = jdbcTemplate.update(SQL_UPDATE, orderItem.getProductId(),
                orderItem.getQuantity(), orderItem.getUpdatedBy(), orderItem.getPriceId(),
                orderItem.getMeasurements(), orderItem.getOrderId(), id);
        if(updated == 1) {
            logger.info("orderItem updated: " + orderItem.getId());
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from order_item where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETE, id);
        if(deleted == 1){
            logger.info("deleted orderItem with an id of " + id);
        }
    }
}
