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

    public static final String CREATE = "INSERT INTO [spring].[dbo].[user] ([user_name], [user_email]) VALUES (?, ?)";
    public static final String UPDATE = "UPDATE [spring].[dbo].[user] SET [spring].[dbo].[user].user_name = ?, [spring].[dbo].[user].[user_email] = ? WHERE user_id = ?";
    public static final String DELETE = "DELETE FROM [spring].[dbo].[user] WHERE user_id = ?";
    public static final String GET_USERS_BY_NAME = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY user_id) AS RowNum, * FROM [spring].[dbo].[user] WHERE [spring].[dbo].[user].[user_name] = ?) "
            + "AS RowConstrainedResult WHERE RowNum > ? AND RowNum <= ?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM [spring].[dbo].[user] WHERE [spring].[dbo].[user].[user_email] = ?";
    public static final String GET_USER_BY_ID = "SELECT * FROM [spring].[dbo].[user] WHERE user_id = ?";

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
        return extendedJDBCTemplate.updateEntity(UPDATE, userMapper, user) > 0 ? user : null;
    }

    @Override
    public boolean delete(Long userId) {
        return extendedJDBCTemplate.update(DELETE, userId) > 0;
    }

    @Override
    public List<User> getUsersByName(String name, int startRow, int lastRow) {
        return extendedJDBCTemplate.query(GET_USERS_BY_NAME, usersResultSetExtractor, name.trim(), startRow, startRow);
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
