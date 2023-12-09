package com.ecommerce.service.priceService;

import com.ecommerce.model.Price;
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
public class PriceServiceImpl implements PriceService<Price> {
    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);
    private JdbcTemplate jdbcTemplate;

    RowMapper<Price>rowMapper = (rs, rowNum) -> {
        Price price = new Price();
        price.setTypeId(rs.getInt("type_id"));
        price.setValue(rs.getInt("value"));
        price.setCurrency(rs.getString("currency"));
        price.setProductId(rs.getInt("product_id"));
        price.setCreatedDate(Instant.parse(rs.getString("created_at")));
        price.setUpdatedDate(Instant.parse(rs.getString("updated_at")));
        price.setCreatedBy(rs.getString("created_by"));
        price.setUpdatedBy(rs.getString("updated_by"));
        return price;
    };

    @Override
    public List<Price> list() {
        String SQL_FIND_ALL = "select * from cart";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(Price price) {
        String SQL_INSERT_INTO = "insert into prices(type_id, value, currency, product_id, created_at, updated_at, created_by, updated_by) values(?,?,?,?,?,?,?,?)";
        final int updated = jdbcTemplate.update(SQL_INSERT_INTO, price.getTypeId(), price.getValue(), price.getCurrency(), price.getProductId(), price.getCreatedDate(), price.getUpdatedDate(), price.getCreatedBy(), price.getUpdatedBy());
        if(updated == 1) {
            logger.info("New price inserted: " + price.getTypeId());
        }

    }

    @Override
    public Optional<Price> get(int id) {
        String SQL_GET_BY_ID = "select value, currency, product_id, created_at, updated_at, created_by, updated_by from prices where type_id=?";
        Price price = null;
        try{
            price = jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id);
        }catch (DataAccessException e){
            logger.info("Not found the price with an id of " + id);
        }
        return Optional.ofNullable(price);
    }

    @Override
    public void update(Price price, int id) {
        String SQL_UPDATE = "update prices set value=?, currency=?, product_id=?, created_at=?, updated_at=?, created_by=?, updated_by=? where type_id=?";
        final int updated = jdbcTemplate.update(SQL_UPDATE, price.getValue(), price.getCurrency(), price.getProductId(), price.getCreatedDate(), price.getUpdatedDate(), price.getCreatedBy(), price.getUpdatedBy(), id);
        if(updated == 1) {
            logger.info("Updated the price with an id " + id);
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETE = "delete from prices where type_id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETE, id);
        if(deleted == 1){
            logger.info("Deleted the price with an id of " + id);
        }
    }
}
