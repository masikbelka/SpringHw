package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.service.UserService;
import com.epam.cdp.spring.util.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long id) {
        User user = null;

        if (id > 0) {
            user = userDao.getUserById(id);
        } else {
            LOGGER.error("Invalid user id " + id);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;

        if (EmailValidator.validate(email)) {
            user = userDao.getUserByEmail(email);
        } else {
            LOGGER.error("Invalid user email " + email);
        }

        return user;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> users = new ArrayList<>();

        if (name != null) {
            users = userDao.getUsersByName(name, pageSize, pageNum);
        } else {
            LOGGER.error("User name can not be null!");
        }

        return users;
    }

    @Override
    public User createUser(User user) {
        User createdUser = null;

        if (isUserValid(user)) {
            createdUser = userDao.create(user);
        } else {
            LOGGER.error("Invalid user : " + String.valueOf(user));
        }

        return createdUser;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = null;

        if (isUserValid(user) && user.getId() > 0) {
            updatedUser = userDao.update(user);
        } else {
            LOGGER.error("Invalid user : " + String.valueOf(user));
        }
        return updatedUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        boolean isDeleted = false;
        if (userId > 0) {
            isDeleted = userDao.delete(userId);
        } else {
            LOGGER.error("Invalid user id " + userId);
        }
        return isDeleted;
    }

    @Override
    public boolean isUserExist(long userId) {
        return getUserById(userId) != null;
    }

    private boolean isUserValid(User user) {
        return user != null && user.getName() != null && !user.getName().isEmpty() && EmailValidator.validate(user.getEmail());
    }
}
