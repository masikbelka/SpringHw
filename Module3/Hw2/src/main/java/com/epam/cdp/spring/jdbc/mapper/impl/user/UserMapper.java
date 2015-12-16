package com.epam.cdp.spring.jdbc.mapper.impl.user;

import com.epam.cdp.spring.jdbc.mapper.InsertQueryMapper;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class UserMapper implements InsertQueryMapper<User>, RowMapper<User> {
    @Override
    public void mapQuery(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getEmail());
    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getUser(rs);
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = new UserImpl();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        user.setEmail(rs.getString("user_email"));
        return user;
    }
}
