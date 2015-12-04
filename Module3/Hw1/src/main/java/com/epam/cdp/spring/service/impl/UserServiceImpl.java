package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.service.EmailValidator;
import com.epam.cdp.spring.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private UserDao userDao;
    private EmailValidator emailValidator;

    public User getUserById(long id) {
        User user = null;

        if (id > 0) {
            user = userDao.getUserById(id);
        } else {
            LOG.warn("You provide wrong id. Id must be more than zero");
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;

        if (emailValidator.validate(email)) {
            user = userDao.getUserByEmail(email);
        } else {
            LOG.warn("You provide wrong id. Id must be more than zero");
        }

        return user;
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<User> users = new ArrayList<>();

        if (name != null && pageSize > 0 && pageNum > 0) {
            users = userDao.getUsersByName(name, pageSize, pageNum);
        } else {
            LOG.warn("You provide wrong params to method.");
        }

        return users;
    }

    public User createUser(User user) {
        User createdUser = null;

        if (isUserValid(user)) {
            createdUser = userDao.create(user);
        } else {
            LOG.warn("Can't create user. It can't be null or with invalid email");
        }

        return createdUser;
    }

    public User updateUser(User user) {
        User updatedUser = null;

        if (isUserValid(user)&& user.getId() > 0) {
            updatedUser = userDao.update(user);
        }
        return updatedUser;
    }

    public boolean deleteUser(long userId) {
        boolean isDeleted = false;
        if (userId > 0) {
            isDeleted = userDao.delete(userId);
        }
        return isDeleted;
    }

    @Override
    public boolean isUserExist(long userId) {
        return getUserById(userId) != null;
    }

    @Required
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Required
    public void setEmailValidator(EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }

    private boolean isUserValid(User user) {
        return user != null && user.getName() != null && !user.getName().isEmpty() && emailValidator.validate(user.getEmail());
    }
}
