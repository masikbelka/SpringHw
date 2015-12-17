package com.epam.cdp.spring.service;

import com.epam.cdp.spring.dao.TicketDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.EventImpl;
import com.epam.cdp.spring.model.impl.TicketImpl;
import com.epam.cdp.spring.model.impl.UserImpl;
import com.epam.cdp.spring.service.impl.TicketServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    private String validUserName = "valid";
    private String validUserEmail = "valid";
    private Ticket validTicket;
    private User validUser;
    private User invalidUser;
    private Event validEvent;
    private Event invalidEvent;
    private String validEventTitle = "valid";
    private Calendar validEventDate = Calendar.getInstance();
    private long validTicketId = 1;
    private long invalidTicketId = 0;
    private long validUserId = 1;
    private long invalidUserId = 0;
    private long validEventId = 1;
    private long invalidEventId = 0;
    private int validTicketPlace = 1;
    private int invalidTicketPlace = 0;
    private TicketCategory validTicketCategory = TicketCategory.STANDARD;
    private TicketCategory invalidTicketCategory = TicketCategory.BAR;
    private List<Ticket> validTicketList;
    private List<Ticket> invalidTicketList;


    @Mock
    private TicketDao ticketDao;
    @InjectMocks
    private TicketService ticketService = new TicketServiceImpl();


    @Before
    public void setUp() throws Exception {
        validTicket = new TicketImpl(validTicketId, validEventId, validUserId, validTicketCategory, validTicketPlace);
        validUser = new UserImpl(validUserId, validUserName, validUserEmail);
        invalidUser = null;
        validEvent = new EventImpl(validEventId, validEventTitle, validEventDate, 0);
        invalidEvent = null;
        validTicketList = new ArrayList<>();
        validTicketList.add(validTicket);
        invalidTicketList = new ArrayList<>();
        when(ticketDao.create(validUserId, validEventId, validTicketPlace, validTicketCategory)).thenReturn(validTicket);
        when(ticketDao.isTicketExist(validEventId, validTicketPlace, validTicketCategory)).thenReturn(true);
        when(ticketDao.isTicketExist(invalidEventId, invalidTicketPlace, invalidTicketCategory)).thenReturn(false);
        when(ticketDao.getBookedTickets(validUser, 1, 1)).thenReturn(validTicketList);
        when(ticketDao.getBookedTickets(invalidUser, 0, 0)).thenReturn(invalidTicketList);
        when(ticketDao.getBookedTickets(validEvent, 1, 1)).thenReturn(validTicketList);
        when(ticketDao.getBookedTickets(invalidEvent, 0, 0)).thenReturn(invalidTicketList);
        when(ticketDao.cancel(validTicketId)).thenReturn(true);
        when(ticketDao.cancel(invalidTicketId)).thenReturn(false);
    }

    @Test
    public void testBookTicket() throws Exception {
        final Ticket actualTicket = ticketService.bookTicket(validUserId, validEventId, validTicketPlace, validTicketCategory);
        assertEquals(validTicket, actualTicket);
    }

    @Test
    public void testBookInvalidTicket() throws Exception {
        assertNull(ticketService.bookTicket(invalidUserId, invalidEventId, invalidTicketPlace, invalidTicketCategory));
    }

    @Test
    public void testGetBookedTicketsByUser() throws Exception {
        final List<Ticket> actual = ticketService.getBookedTickets(validUser, 1, 1);
        assertEquals(validTicketList, actual);
    }

    @Test
    public void testGetInvalidBookedTicketsByUser() throws Exception {
        final List<Ticket> actual = ticketService.getBookedTickets(invalidUser, 0, 0);
        assertEquals(invalidTicketList, actual);
    }

    @Test
    public void testGetBookedTicketsByEvent() throws Exception {
        final List<Ticket> actual = ticketService.getBookedTickets(invalidEvent, 0, 0);
        assertEquals(invalidTicketList, actual);
    }

    @Test
    public void testGetInvalidBookedTicketsByEvent() throws Exception {
        final List<Ticket> actual = ticketService.getBookedTickets(invalidEvent, 0, 0);
        assertEquals(invalidTicketList, actual);
    }

    @Test
    public void testCancelTicket() throws Exception {
        assertTrue(ticketService.cancelTicket(validTicketId));
    }

    @Test
    public void testCancelInvalidTicket() throws Exception {
        assertFalse(ticketService.cancelTicket(invalidTicketId));
    }
}