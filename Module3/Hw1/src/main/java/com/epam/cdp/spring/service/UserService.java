package com.epam.cdp.spring.service;

import com.epam.cdp.spring.model.User;

import java.util.List;

public interface UserService {
    User getUserById(long id);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(long userId);
}
