package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UserDaoImpl implements UserDao {

    @Autowired
    private Map<Long, User> userStorage;

}
