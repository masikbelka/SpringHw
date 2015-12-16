package com.epam.cdp.spring.service;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import com.epam.cdp.spring.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    int validPageSize = 1;
    int validPageNum = 1;
    int invalidPageSize = 1;
    int invalidPageNum = 1;
    private String validUserName = "valid";
    private String invalidUserName = "invalid";
    private String validUserEmail = "valid@email.com";
    private String invalidUserEmail = "invalid";
    private User validUser;
    private User invalidUser;
    private long validUserId = 1;
    private long invalidUserId = 0;
    private List<User> validUserList;
    private List<User> invalidUserList;

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void setUp() throws Exception {
        validUser = new UserImpl(validUserId, validUserName, validUserEmail);
        invalidUser = null;
        when(userDao.getUserById(validUserId)).thenReturn(validUser);
        when(userDao.getUserById(invalidUserId)).thenReturn(invalidUser);
        when(userDao.getUserByEmail(validUserEmail)).thenReturn(validUser);
        when(userDao.getUserByEmail(invalidUserEmail)).thenReturn(invalidUser);
        invalidUserList = new ArrayList<>();
        validUserList = new ArrayList<>();
        validUserList.add(validUser);
        when(userDao.getUsersByName(validUserName, validPageSize, validPageNum)).thenReturn(validUserList);
        when(userDao.getUsersByName(invalidUserName, invalidPageSize, invalidPageNum)).thenReturn(invalidUserList);
        when(userDao.create(validUser)).thenReturn(validUser);
        when(userDao.create(invalidUser)).thenReturn(invalidUser);
        when(userDao.update(validUser)).thenReturn(validUser);
        when(userDao.update(invalidUser)).thenReturn(invalidUser);
        when(userDao.delete(validUserId)).thenReturn(true);
        when(userDao.delete(invalidUserId)).thenReturn(false);
    }

    @Test
    public void testGetUserById() throws Exception {
        final User actual = userService.getUserById(validUserId);
        assertEquals(validUser, actual);

    }

    @Test
    public void testGetUserByInvalidId() throws Exception {
        final User actual = userService.getUserById(invalidUserId);
        assertEquals(invalidUser, actual);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        assertEquals(validUser, userService.getUserByEmail(validUserEmail));
    }

    @Test
    public void testGetUsersByInvalidEmail() throws Exception {
        assertEquals(invalidUser, userService.getUserByEmail(invalidUserEmail));
    }

    @Test
    public void testGetUsersByName() throws Exception {
        assertEquals(validUserList, userService.getUsersByName(validUserName, validPageSize, validPageNum));
    }

    @Test
    public void testGetUsersByInvalidName() throws Exception {
        assertEquals(invalidUserList, userService.getUsersByName(invalidUserName, invalidPageSize, invalidPageNum));
    }

    @Test
    public void testCreateUser() throws Exception {
        assertEquals(validUser, userService.createUser(validUser));
    }

    @Test
    public void testCreateInvalidUser() throws Exception {
        assertEquals(invalidUser, userService.createUser(invalidUser));
    }

    @Test
    public void testUpdateUser() throws Exception {
        assertEquals(validUser, userService.updateUser(validUser));
    }

    @Test
    public void testUpdateInvalidUser() throws Exception {
        assertEquals(invalidUser, userService.updateUser(invalidUser));
    }

    @Test
    public void testDeleteUser() throws Exception {
        assertTrue(userService.deleteUser(validUserId));
    }

    @Test
    public void testDeleteInvalidUser() throws Exception {
        assertFalse(userService.deleteUser(invalidUserId));
    }

    @Test
    public void testIsUserExist() throws Exception {
        assertTrue(userService.isUserExist(validUserId));
    }

    @Test
    public void testIsNotUserExist() throws Exception {
        assertFalse(userService.isUserExist(invalidUserId));
    }
}