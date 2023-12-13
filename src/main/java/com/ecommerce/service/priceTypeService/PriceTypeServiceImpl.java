package com.ecommerce.service.priceTypeService;

import com.ecommerce.model.PriceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PriceTypeServiceImpl implements PriceTypeService {
    private static final Logger logger = LoggerFactory.getLogger(PriceTypeServiceImpl.class);
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PriceTypeServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper<PriceType> rowMapper = (rs, rowNum) -> {
        PriceType priceType = new PriceType();
        priceType.setId(rs.getInt("id"));
        priceType.setName(rs.getString("name"));
        if(rs.getTimestamp("created_at")!=null){
            priceType.setCreatedDate(rs.getTimestamp("created_at").toInstant());
        }
        if(rs.getTimestamp("updated_at") !=null) {
            priceType.setUpdatedDate(rs.getTimestamp("updated_at").toInstant());
        }
        priceType.setCreatedBy(rs.getString("created_by"));
        priceType.setUpdatedBy(rs.getString("updated_by"));
        return priceType;
    };

    @Override
    public List<PriceType> list() {
        String SQL_FIND_ALL = "select * from price_types";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(PriceType priceType) {
        String SQL_INSERT_INTO = "insert into price_types(name, created_by) values(?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSERT_INTO, priceType.getName(), priceType.getCreatedBy());
        if (inserted == 1) {
            logger.info("New priceType inserted: " + priceType.getName());
        }
    }

    @Override
    public PriceType get(int id) {
        String SQL_GET_BY_ID = "select id, name, created_at, updated_at, created_by, updated_by from price_types where id=?";
            Optional<PriceType> priceType = Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id));
            return priceType.orElse(null);
    }

    @Override
    public void update(PriceType priceType, int id) {
        String SQL_UPDATE = "update price_types set name=?, updated_at=current_timestamp,  updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_UPDATE, priceType.getName(), priceType.getUpdatedBy(), id);
        if (updated == 1) {
            logger.info("Updated priceType with an id of " + id);
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETED = "delete from price_types where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETED, id);
        if (deleted == 1) {
            logger.info("Deleted the priceType with and Id of " + id);
        }
    }
}
