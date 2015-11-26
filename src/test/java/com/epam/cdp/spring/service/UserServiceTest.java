package com.epam.cdp.spring.service;

import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private String validUserName = "valid";
    private String invalidUserName = "invalid";
    private String validUserEmail = "valid";
    private String invalidUserEmail = "invalid";
    private Ticket validTicket;
    private Ticket invalidTicket;
    private User validUser;
    private User invalidUser;
    private Event validEvent;
    private Event invalidEvent;
    private String validEventTitle = "valid";
    private String invalidEventTitle = "invalid";
    private Date validEventDate = new Date();
    private Date invalidEventDate = new Date();
    private long validTicketId = 1;
    private long invalidTicketId = 0;
    private long validUserId = 1;
    private long invalidUserId = 0;
    private long validEventId = 1;
    private long invalidEventId = 0;
    private int validTicketPlace = 1;
    private int invalidTicketPlace = 0;
    private Ticket.Category validTicketCategory = Ticket.Category.STANDARD;
    private Ticket.Category invalidTicketCategory = Ticket.Category.BAR;
    private List<User> validUserList;
    private List<User> invalidUserList;

    @Mock
    private UserDao userDao;

    @Mock
    private EmailValidator emailValidator;
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void setUp() throws Exception {
        validUser = new UserImpl(validUserId, validUserName, validUserEmail);
        invalidUser = null;
        when(userDao.getUserById(validUserId)).thenReturn(validUser);
        when(userDao.getUserById(invalidUserId)).thenReturn(invalidUser);
        invalidUserList = new ArrayList<>();
        validUserList = new ArrayList<>();
        validUserList.add(validUser);

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


    }

    @Test
    public void testGetUsersByName() throws Exception {

    }

    @Test
    public void testCreateUser() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testIsUserExist() throws Exception {

    }
}