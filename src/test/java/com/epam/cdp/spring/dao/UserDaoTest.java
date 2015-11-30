package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.dao.impl.UserDaoImpl;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"/test-config.xml"})
@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {

    int validPageSize = 1;
    int validPageNum = 1;
    int invalidPageSize = 1;
    int invalidPageNum = 1;
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

    @Autowired
    private Map<Long, User> userStorage;

    @InjectMocks
    private UserDao userDao = new UserDaoImpl();
    @Before
    public void setUp() throws Exception {
        validUser = new UserImpl(validUserId, validUserName, validUserEmail);
        invalidUser = null;
        invalidUserList = new ArrayList<>();
        validUserList = new ArrayList<>();
        validUserList.add(validUser);
        //when(userStorage.put(validUserId, validUser)).then(u)
    }
    @Test
    public void testCreate() throws Exception {
        userDao.create(validUser);
        assertEquals(1, userStorage.size());
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGetUsersByName() throws Exception {

    }

    @Test
    public void testGetUserByEmail() throws Exception {

    }

    @Test
    public void testGetUserById() throws Exception {

    }
}