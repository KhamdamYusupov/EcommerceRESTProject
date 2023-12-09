package com.ecommerce.service.priceTypeService;

import com.ecommerce.model.PriceType;
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
public class PriceTypeServiceImpl implements PriceTypeService<PriceType> {
    private static final Logger logger = LoggerFactory.getLogger(PriceTypeServiceImpl.class);
    JdbcTemplate jdbcTemplate;
    RowMapper<PriceType> rowMapper = (rs, rowNum) -> {
        PriceType priceType = new PriceType();
        priceType.setId(rs.getInt("id"));
        priceType.setName(rs.getString("name"));
        priceType.setCreatedDate(Instant.parse(rs.getString("created_at")));
        priceType.setUpdatedDate(Instant.parse(rs.getString("updated_at")));
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
        String SQL_INSET_INTO = "insert into price_types(id, name, created_at, updated_at, created_by, updated_by) values(?,?,?,?,?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSET_INTO, priceType.getId(), priceType.getName(), priceType.getCreatedDate(), priceType.getCreatedBy(), priceType.getUpdatedBy());
        if (inserted == 1) {
            logger.info("New priceType inserted: " + priceType.getName());
        }
    }

    @Override
    public Optional<PriceType> get(int id) {
        String SQL_GET_BY_ID = "select name, created_at, updated_at, created_by, updated_by from price_types where id=?";
        PriceType priceType = null;
        try {
            priceType = jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id);
        } catch (DataAccessException e) {
            logger.info("Could not find priceType with an Id of " + id);
        }
        return Optional.ofNullable(priceType);
    }

    @Override
    public void update(PriceType priceType, int id) {
        String SQL_UPDATE = "update price_types set name=?, created_at=?, updated_at=?, created_by=?, updated_by=? where id=?";
        final int updated = jdbcTemplate.update(SQL_UPDATE, priceType.getName(), priceType.getCreatedDate(), priceType.getUpdatedDate(), priceType.getCreatedBy(), priceType.getUpdatedBy(), id);
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
