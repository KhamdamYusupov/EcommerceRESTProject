package com.ecommerce.service.userService;

import com.ecommerce.model.User;
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
public class UserServiceImpl implements UserService<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    JdbcTemplate jdbcTemplate;

    RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCreatedDate(Instant.parse(rs.getString("created_at")));
        user.setUpdatedDate(Instant.parse(rs.getString("updated_at")));
        user.setCreatedBy(rs.getString("created_by"));
        user.setUpdatedBy(rs.getString("updated_by"));
        return user;
    };

    @Override
    public List<User> list() {
        String SQL_FIND_ALL = "select * from users";
        return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
    }

    @Override
    public void create(User user) {
        String SQL_INSERT_INTO = "insert into users(id, first_name, last_name, created_at, updated_at, created_by, updated_by) values(?,?,?,?,?,?,?)";
        final int inserted = jdbcTemplate.update(SQL_INSERT_INTO, user.getId(), user.getFirstName(), user.getLastName(), user.getCreatedDate(), user.getUpdatedDate(), user.getCreatedBy(), user.getUpdatedBy());
        if (inserted == 1) {
            logger.info("Inserted new user with an Id of " + user.getId());
        }
    }

    @Override
    public Optional<User> get(int id) {
        String SQL_GET_BY_ID = "select first_name, last_name, created_at, updated_at, created_by, updated_by from users where id=?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SQL_GET_BY_ID, rowMapper, id);
        } catch (DataAccessException e) {
            logger.info("Could not found user with an id of " + id);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void update(User user, int id) {
        String SQL_UPDATE = "update users set first_name=?, last_name=?, created_at=?, updated_at=?, created_by=?, updated_by=? where id =?";
        final int updated = jdbcTemplate.update(SQL_UPDATE, user.getFirstName(), user.getLastName(), user.getCreatedDate(), user.getUpdatedDate(), user.getCreatedBy(), user.getUpdatedBy(), id);
        if (updated == 1) {
            logger.info("Updated the user wiyh an Id of " + id);
        }
    }

    @Override
    public void delete(int id) {
        String SQL_DELETED = "delete from users where id=?";
        final int deleted = jdbcTemplate.update(SQL_DELETED, id);
        if (deleted == 1) {
            logger.info("Deleted the user with an id of " + id);
        }
    }
}
