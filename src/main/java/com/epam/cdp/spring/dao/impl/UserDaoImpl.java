package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {

    private long lastId;

    @Resource
    private Map<Long, User> userStorage;

    public UserDaoImpl() {
        this.lastId = 1;
    }

    @Override
    public User create(User user) {
        long id = lastId++;
        User puttedUser = new UserImpl(id, user.getEmail(), user.getName());
        userStorage.put(id, puttedUser);
        return puttedUser;
    }

    @Override
    public User update(User user) {
        return userStorage.replace(user.getId(), user);
    }

    @Override
    public boolean delete(long userId) {
        return userStorage.remove(userId) != null;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final List<User> resultUsers = userStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getName().equals(name))
                .map(Map.Entry::getValue).collect(Collectors.toList());

        return getPage(pageSize, pageNum, resultUsers);
    }

    @Override
    public User getUserByEmail(String email) {
        return userStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getName().equals(email))
                .findFirst().get().getValue();
    }

    @Override
    public User getUserById(long id) {
        return userStorage.get(id);
    }

    private List<User> getPage(int pageSize, int pageNum, List<User> users) {
        int startPoint = pageSize * (pageNum - 1);
        int endPoint = pageSize * pageNum - 1;
        int size = users.size();

        if (size >= startPoint && pageNum > 0 && pageSize > 0) {
            return users.subList(startPoint, endPoint > size ? size : endPoint);
        }
        return users;
    }
}
