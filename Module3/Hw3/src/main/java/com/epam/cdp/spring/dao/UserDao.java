package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.model.User;

import java.util.List;

public interface UserDao {
    User create(User user);

    User update(User user);

    boolean delete(Long userId);

    List<User> getUsersByName(String name, int startRow, int lastRow);

    User getUserByEmail(String email);

    User getUserById(long id);
}
