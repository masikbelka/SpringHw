package com.epam.cdp.spring.facade;

import com.epam.cdp.spring.exception.BookingFacadeException;
import com.epam.cdp.spring.model.*;
import com.epam.cdp.spring.model.impl.EventImpl;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:storage.xml")
@Transactional
public class BookingFacadeTest {

    public static final int INVALID_ID = 9999;
    public static final int FIRST_ID = 1;
    public static final int SECOND_ID = 2;
    public static final int THIRD_ID = 3;
    public static final int FOURTH_ID = 4;
    public static final String EVENT = "Event";
    public static final String SECOND_EVENT_DATE = "2015-06-01";
    public static final String TEST_EVENT_DATE = "2015-01-01";
    public static final String CHANGED_TITLE = "CHANGED";
    public static final String TEST_EVENT_TITLE = "Test Event";
    public static final int INVALID_EVENT_ID = 100;
    public static final String SECOND_USER_EMAIL = "second@mail.com";
    public static final String TEST_USER_NAME = "Test User";
    public static final String TEST_USER_EMAIL = "testUser@mail.com";
    public static final String NOT_EXISTING_EMAIL = "test@mail.test";
    public static final String INVALID_EMAIL = "a.a";
    public SimpleDateFormat simpleDateFormat;

    @Autowired
    private BookingFacade bookingFacade;

    @Before
    public void setUp() throws Exception {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    @Rollback(true)
    public void testGetEventById() throws Exception {
        Event event = bookingFacade.getEventById(FIRST_ID);
        assertNotNull(event);
    }

    @Test
    @Rollback(true)
    public void testGetEventByInvalidId() throws Exception {
        Event event = bookingFacade.getEventById(INVALID_ID);
        assertNull(event);
    }

    @Test
    @Rollback(true)
    public void testGetEventsByTitle() throws Exception {
        List<Event> events = bookingFacade.getEventsByTitle(EVENT, 5, 1);
        assertEquals(4, events.size());

    }

    @Test
    @Rollback(true)
    public void testGetFirstEventsPageByTitle() throws Exception {
        List<Event> events = bookingFacade.getEventsByTitle(EVENT, 2, 1);
        assertEquals(2, events.size());
    }

    @Test
    @Rollback(true)
    public void testGetPartiallyFilledEventsPageByTitle() throws Exception {
        List<Event> events = bookingFacade.getEventsByTitle(EVENT, 3, 2);
        assertEquals(1, events.size());
    }

    @Test
    @Rollback(true)
    public void testGetEventsForDay() throws Exception {
        Calendar date = Calendar.getInstance();
        date.setTime(simpleDateFormat.parse(SECOND_EVENT_DATE));
        List<Event> events = bookingFacade.getEventsForDay(date, 4, 1);
        assertEquals(1, events.size());
    }

    @Test
    @Rollback(true)
    public void testIfGetEventsForDayReturnCorrectEvents() throws Exception {
        Calendar date = Calendar.getInstance();
        date.setTime(simpleDateFormat.parse(SECOND_EVENT_DATE));
        List<Event> events = bookingFacade.getEventsForDay(date, 4, 1);
        assertEquals(bookingFacade.getEventById(SECOND_ID), events.get(0));
    }

    @Test
    @Rollback(true)
    public void testCreateEvent() throws Exception {
        Calendar testDate = Calendar.getInstance();
        testDate.setTime(simpleDateFormat.parse(TEST_EVENT_DATE));
        Event event = new EventImpl("Test Event", testDate, 0);
        Event createdEvent = bookingFacade.createEvent(event);
        assertTrue(createdEvent.getId() > 0);
    }

    @Test
    @Rollback(true)
    public void testCreateNotValidEventWithoutDate() throws Exception {
        Event event = new EventImpl(TEST_EVENT_TITLE, null, 0);
        Event createdEvent = bookingFacade.createEvent(event);
        assertNull(createdEvent);
    }

    @Test
    @Rollback(true)
    public void testCreateNotValidEventWithoutTitle() throws Exception {
        Calendar testDate = Calendar.getInstance();
        testDate.setTime(simpleDateFormat.parse(SECOND_EVENT_DATE));
        Event event = new EventImpl(null, testDate, 0);
        Event createdEvent = bookingFacade.createEvent(event);
        assertNull(createdEvent);
    }

    @Test
    @Rollback(true)
    public void testUpdateEvent() throws Exception {
        Calendar testDate = Calendar.getInstance();
        testDate.setTime(simpleDateFormat.parse(TEST_EVENT_DATE));
        Event event = new EventImpl(THIRD_ID, TEST_EVENT_TITLE, testDate, 0);
        Event previousEvent = bookingFacade.getEventById(THIRD_ID);
        Event updatedEvent = bookingFacade.updateEvent(event);
        assertNotEquals(previousEvent, updatedEvent);
    }

    @Test
    @Rollback(true)
    public void testUpdateEventWithoutDate() throws Exception {
        Event event = new EventImpl(SECOND_ID, TEST_EVENT_TITLE, null, 0);
        assertNull(bookingFacade.updateEvent(event));
    }

    @Test
    @Rollback(true)
    public void testUpdateEventWithoutTitle() throws Exception {
        Calendar testDate = Calendar.getInstance();
        testDate.setTime(simpleDateFormat.parse(TEST_EVENT_DATE));
        Event event = new EventImpl(SECOND_ID, null, testDate, 0);
        assertNull(bookingFacade.updateEvent(event));
    }

    @Test
    @Rollback(true)
    public void testUpdateEventWithInvalidId() throws Exception {
        Calendar testDate = Calendar.getInstance();
        testDate.setTime(simpleDateFormat.parse(TEST_EVENT_DATE));
        Event event = new EventImpl(0, TEST_EVENT_TITLE, testDate, 0);
        assertNull(bookingFacade.updateEvent(event));
    }

    @Test
    @Rollback(true)
    @DependsOn("testCreateEvent")
    public void testDeleteEvent() throws Exception {
        Event event = bookingFacade.getEventById(FOURTH_ID);
        assertTrue(bookingFacade.deleteEvent(FOURTH_ID));
    }

    @Test
    @Rollback(true)
    public void testDeleteInvalidEvent() throws Exception {
        assertFalse(bookingFacade.deleteEvent(INVALID_EVENT_ID));
    }

    @Test
    @Rollback(true)
    public void testGetUserByEmail() throws Exception {
        assertNotNull(bookingFacade.getUserByEmail(SECOND_USER_EMAIL));
    }

    @Test
    @Rollback(true)
    public void testGetUserByNullEmail() throws Exception {
        assertNull(bookingFacade.getUserByEmail(null));
    }

    @Test
    @Rollback(true)
    public void testGetUserByEmptyEmail() throws Exception {
        assertNull(bookingFacade.getUserByEmail(""));
    }

    @Test
    @Rollback(true)
    public void testGetUserByNotExistingEmail() throws Exception {
        assertNull(bookingFacade.getUserByEmail(NOT_EXISTING_EMAIL));
    }

    @Test
    @Rollback(true)
    public void testCreateUser() throws Exception {
        User user = new UserImpl(TEST_USER_NAME, TEST_USER_EMAIL);
        User createdUser = bookingFacade.createUser(user);
        assertNotEquals(0, createdUser.getId());
    }

    @Test
    @Rollback(true)
    public void testCreateUserWithInvalidEmail() throws Exception {
        User user = new UserImpl(TEST_USER_NAME, INVALID_EMAIL);
        User createdUser = bookingFacade.createUser(user);
        assertNull(createdUser);
    }

    @Test
    @Rollback(true)
    public void testCreateUserWithNullEmail() throws Exception {
        User user = new UserImpl(TEST_USER_NAME, null);
        User createdUser = bookingFacade.createUser(user);
        assertNull(createdUser);
    }

    @Test
    @Rollback(true)
    public void testCreateUserWithNullName() throws Exception {
        User user = new UserImpl(null, TEST_USER_EMAIL);
        User createdUser = bookingFacade.createUser(user);
        assertNull(createdUser);
    }

    @Test
    @Rollback(true)
    public void testUpdateUser() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, TEST_USER_EMAIL);
        User previousUser = bookingFacade.getUserById(SECOND_ID);
        User updatedUser = bookingFacade.updateUser(user);
        assertNotEquals(previousUser, updatedUser);
    }

    @Test
    @Rollback(true)
    public void testUpdateUserWithNullName() throws Exception {
        User user = new UserImpl(SECOND_ID, null, TEST_USER_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    @Rollback(true)
    public void testUpdateUserWithEmptyName() throws Exception {
        User user = new UserImpl(SECOND_ID, "", TEST_USER_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    @Rollback(true)
    public void testUpdateUserWithNullEmail() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, null);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    @Rollback(true)
    public void testUpdateUserWithEmptyEmail() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, "");
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    @Rollback(true)
    public void testUpdateUserWithInvalidEmail() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, INVALID_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    @Rollback(true)
    public void testUpdateUserWithInvalidId() throws Exception {
        User user = new UserImpl(INVALID_ID, TEST_USER_NAME, TEST_USER_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    @Rollback(true)
    public void testDeleteUser() throws Exception {
        assertTrue(bookingFacade.deleteUser(SECOND_ID));
        assertNull(bookingFacade.getUserById(SECOND_ID));
    }

    @Test
    @Rollback(true)
    public void testDeleteUserWithInvalidId() throws Exception {
        assertFalse(bookingFacade.deleteUser(INVALID_ID));
    }

    @Test
    @Rollback(true)
    public void testBookTicket() throws Exception {
        assertNotNull(bookingFacade.bookTicket(FOURTH_ID, SECOND_ID, 1, TicketCategory.PREMIUM));
    }

    @Test
    @Rollback(true)
    public void testBookTicketWithInvalidUserId() throws Exception {
        assertNull(bookingFacade.bookTicket(INVALID_ID, SECOND_ID, 2, TicketCategory.PREMIUM));
    }

    @Test
    @Rollback(true)
    public void testBookTicketWithInvalidEventId() throws Exception {
        assertNull(bookingFacade.bookTicket(FOURTH_ID, INVALID_ID, 2, TicketCategory.PREMIUM));
    }

    @Test
    @Rollback(true)
    public void testBookTicketWithNullCategory() throws Exception {
        assertNull(bookingFacade.bookTicket(FOURTH_ID, SECOND_ID, 2, null));
    }

    @Test
    @Rollback(true)
    public void testBookBookedTicket() throws Exception {
        assertNull(bookingFacade.bookTicket(FOURTH_ID, FIRST_ID, 1, TicketCategory.BAR));
    }

    @Test
    @Rollback(true)
    public void testGetBookedTickets() throws Exception {
        User user = bookingFacade.getUserById(FIRST_ID);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 4, 1);
        assertNotEquals(0, tickets);
        for (Ticket ticket : tickets) {
            assertEquals(user.getId(), ticket.getUserId());
        }
    }

    @Test
    @Rollback(true)
    public void testGetBookedTicketWithNullUser() throws Exception {
        User user = null;
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 4, 1);
        assertEquals(0, tickets.size());
    }

    @Test
    @Rollback(true)
    public void testWithdrawFunds() throws Exception {
        UserAccount userAccount = bookingFacade.getUserAccountById(1);
        bookingFacade.withdrawFunds(userAccount.getUserAccountId(), 1);
        UserAccount updatedUserAccount = bookingFacade.getUserAccountById(1);
        assertEquals(userAccount.getPrepaidMoney() - 1, updatedUserAccount.getPrepaidMoney(), 1);
    }

    @Test(expected = BookingFacadeException.class)
    @Rollback(true)
    public void testWithdrawInvalidFunds() throws Exception {
        bookingFacade.withdrawFunds(1, -1);
    }

    @Test(expected = BookingFacadeException.class)
    @Rollback(true)
    public void testWithdrawMoreFundsThatAvailable() throws Exception {
        bookingFacade.withdrawFunds(1, Double.MAX_VALUE);
    }

    @Test
    @Rollback(true)
    public void testAddFunds() throws Exception {
        UserAccount userAccount = bookingFacade.getUserAccountById(1);
        bookingFacade.addFunds(userAccount.getUserAccountId(), 1);
        UserAccount updatedUserAccount = bookingFacade.getUserAccountById(1);
        assertEquals(userAccount.getPrepaidMoney() + 1, updatedUserAccount.getPrepaidMoney(), 1);
    }

    @Test(expected = BookingFacadeException.class)
    @Rollback(true)
    public void testAddInvalidFunds() throws Exception {
        bookingFacade.addFunds(1, -1);
    }

    @Test
    @Rollback(true)
    public void testAddFundsWhenAccountNoxExist() throws Exception {
        assertFalse(bookingFacade.addFunds(10, 1));
    }

    @Test
    @Rollback(true)
    public void testWithdrawFundsWhenAccountNotExist() throws Exception {
        assertFalse(bookingFacade.withdrawFunds(10, 1));

    }
}