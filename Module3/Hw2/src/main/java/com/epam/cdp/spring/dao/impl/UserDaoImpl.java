package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.jdbc.ExtendedJDBCTemplate;
import com.epam.cdp.spring.jdbc.extractor.user.UsersResultSetExtractor;
import com.epam.cdp.spring.jdbc.mapper.impl.user.UserMapper;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    public static final String CREATE = "INSERT INTO [user] (name, email) VALUES (?, ?)";
    public static final String UPDATE = "UPDATE [user] SET name = ?, email = ?";
    public static final String DELETE = "DELETE FROM [user] WHERE user_id = ?";
    public static final String GET_USERS_BY_NAME = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY user_id) AS RowNum, * FROM [user] WHERE name = ?) "
            + "AS RowConstrainedResult WHERE RowNum > ? AND RowNum <= ?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM [user] WHERE email = ?";
    public static final String GET_USER_BY_ID = "SELECT * FROM [user] WHERE user_id = ?";

    @Autowired
    private ExtendedJDBCTemplate extendedJDBCTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UsersResultSetExtractor usersResultSetExtractor;


    public UserDaoImpl() {
    }

    @Override
    public User create(User user) {
        User createdUser = null;
        Number generatedId = extendedJDBCTemplate.addAndReturnGeneratedKey(CREATE, userMapper, user);
        if(generatedId != null){
            createdUser = cloneUser(user);
            createdUser.setId(generatedId.longValue());
        }
        return createdUser;
    }

    @Override
    public User update(User user) {
        return extendedJDBCTemplate.update(UPDATE, userMapper, user) > 0 ? user : null;
    }

    @Override
    public boolean delete(long userId) {
        return extendedJDBCTemplate.queryForObject(DELETE, Integer.class, userId) > 0;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        int startRow = pageSize * pageNum;
        return extendedJDBCTemplate.query(GET_USERS_BY_NAME, usersResultSetExtractor, name.trim(), startRow, pageSize);
    }

    @Override
    public User getUserByEmail(String email) {
        return extendedJDBCTemplate.queryForObject(GET_USER_BY_EMAIL, userMapper, email);
    }

    @Override
    public User getUserById(long id) {
        return extendedJDBCTemplate.queryForObject(GET_USER_BY_ID, userMapper, id);
    }

    private User cloneUser(User user) {
        User clonedUser = new UserImpl();
        clonedUser.setName(user.getName());
        clonedUser.setEmail(user.getEmail());
        return clonedUser;
    }
}
