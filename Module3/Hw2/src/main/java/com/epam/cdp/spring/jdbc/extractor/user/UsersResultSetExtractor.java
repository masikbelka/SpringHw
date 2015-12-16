package com.epam.cdp.spring.jdbc.extractor.user;

import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class UsersResultSetExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = getUser(resultSet);
            users.add(user);
        }
        return users;
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = new UserImpl();
        user.setId(rs.getLong("user_id"));
        user.setName(rs.getString("user_name"));
        user.setEmail(rs.getString("user_email"));
        return user;
    }
}
