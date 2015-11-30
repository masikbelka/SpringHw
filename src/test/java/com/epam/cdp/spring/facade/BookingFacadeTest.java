package com.epam.cdp.spring.facade;

import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.EventImpl;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-context.xml")
public class BookingFacadeTest {

    public static final int INVALID_ID = 9999;
    public static final int FIRST_ID = 1;
    public static final int SECOND_ID = 2;
    public static final int THIRD_ID = 3;
    public static final int FOURTH_ID = 4;
    public static final String EVENT = "Event";
    public static final String SECOND_EVENT_DATE = "10/11/2015";
    public static final String TEST_EVENT_DATE = "1/1/2016";
    public static final String CHANGED_TITLE = "CHANGED";
    public static final String TEST_EVENT_TITLE = "Test Event";
    public static final int INVALID_EVENT_ID = 100;
    public static final String SECOND_USER_EMAIL = "2user@mail.com";
    public static final String TEST_USER_NAME = "Test User";
    public static final String TEST_USER_EMAIL = "testUser@mail.com";
    public static final String NOT_EXISTING_EMAIL = "test@mail.test";
    public static final String INVALID_EMAIL = "a.a";
    public SimpleDateFormat simpleDateFormat;

    @Autowired
    private BookingFacade bookingFacade;

    @Before
    public void setUp() throws Exception {
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    public void testGetEventById() throws Exception {
        Event event = bookingFacade.getEventById(FIRST_ID);
        assertNotNull(event);
    }

    @Test
    public void testGetEventByInvalidId() throws Exception {
        Event event = bookingFacade.getEventById(INVALID_ID);
        assertNull(event);
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        List<Event> events = bookingFacade.getEventsByTitle(EVENT, 5, 1);
        assertEquals(4, events.size());

    }

    @Test
    public void testGetFirstEventsPageByTitle() throws Exception {
        List<Event> events = bookingFacade.getEventsByTitle(EVENT, 2, 1);
        assertEquals(2, events.size());
    }

    @Test
    public void testGetPartiallyFilledEventsPageByTitle() throws Exception {
        List<Event> events = bookingFacade.getEventsByTitle(EVENT, 3, 2);
        assertEquals(1, events.size());

    }

    @Test
    public void testGetEventsForDay() throws Exception {
        Date date = simpleDateFormat.parse(SECOND_EVENT_DATE);
        List<Event> events = bookingFacade.getEventsForDay(date, 4, 1);
        assertEquals(1, events.size());
    }

    @Test
    public void testIfGetEventsForDayReturnCorrectEvents() throws Exception {
        Date date = simpleDateFormat.parse(SECOND_EVENT_DATE);
        List<Event> events = bookingFacade.getEventsForDay(date, 4, 1);
        assertEquals(bookingFacade.getEventById(SECOND_ID), events.get(0));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Date testDate = simpleDateFormat.parse(TEST_EVENT_DATE);
        Event event = new EventImpl("Test Event", testDate);
        Event createdEvent = bookingFacade.createEvent(event);
        assertNotEquals(0, createdEvent.getId());
    }

    @Test
    public void testCreateNotValidEventWithoutDate() throws Exception {
        Event event = new EventImpl(TEST_EVENT_TITLE, null);
        Event createdEvent = bookingFacade.createEvent(event);
        assertNull(createdEvent);
    }

    @Test
    public void testCreateNotValidEventWithoutTitle() throws Exception {
        Date testDate = simpleDateFormat.parse(SECOND_EVENT_DATE);
        Event event = new EventImpl(null, testDate);
        Event createdEvent = bookingFacade.createEvent(event);
        assertNull(createdEvent);
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Date testDate = simpleDateFormat.parse(TEST_EVENT_DATE);
        Event event = new EventImpl(SECOND_ID, TEST_EVENT_TITLE, testDate);
        Event previousEvent = bookingFacade.getEventById(SECOND_ID);
        Event updatedEvent = bookingFacade.updateEvent(event);
        assertEquals(previousEvent, updatedEvent);
    }

    @Test
    public void testUpdateEventWithoutDate() throws Exception {
        Event event = new EventImpl(SECOND_ID, TEST_EVENT_TITLE, null);
        assertNull(bookingFacade.updateEvent(event));
    }

    @Test
    public void testUpdateEventWithoutTitle() throws Exception {
        Date testDate = simpleDateFormat.parse(TEST_EVENT_DATE);
        Event event = new EventImpl(SECOND_ID, null, testDate);
        assertNull(bookingFacade.updateEvent(event));
    }

    @Test
    public void testUpdateEventWithInvalidId() throws Exception {
        Date testDate = simpleDateFormat.parse(TEST_EVENT_DATE);
        Event event = new EventImpl(0, TEST_EVENT_TITLE, testDate);
        assertNull(bookingFacade.updateEvent(event));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        assertTrue(bookingFacade.deleteEvent(SECOND_ID));
    }

    @Test
    public void testDeleteInvalidEvent() throws Exception {
        assertFalse(bookingFacade.deleteEvent(INVALID_EVENT_ID));
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        assertNotNull(bookingFacade.getUserByEmail(SECOND_USER_EMAIL));
    }

    @Test
    public void testGetUserByNullEmail() throws Exception {
        assertNull(bookingFacade.getUserByEmail(null));
    }

    @Test
    public void testGetUserByEmptyEmail() throws Exception {
        assertNull(bookingFacade.getUserByEmail(""));
    }

    @Test
    public void testGetUserByNotExistingEmail() throws Exception {
        assertNull(bookingFacade.getUserByEmail(NOT_EXISTING_EMAIL));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new UserImpl(TEST_USER_NAME, TEST_USER_EMAIL);
        User createdUser = bookingFacade.createUser(user);
        assertNotEquals(0, createdUser.getId());
    }

    @Test
    public void testCreateUserWithInvalidEmail() throws Exception {
        User user = new UserImpl(TEST_USER_NAME, INVALID_EMAIL);
        User createdUser = bookingFacade.createUser(user);
        assertNull(createdUser);
    }

    @Test
    public void testCreateUserWithNullEmail() throws Exception {
        User user = new UserImpl(TEST_USER_NAME, null);
        User createdUser = bookingFacade.createUser(user);
        assertNull(createdUser);
    }

    @Test
    public void testCreateUserWithNullName() throws Exception {
        User user = new UserImpl(null, TEST_USER_EMAIL);
        User createdUser = bookingFacade.createUser(user);
        assertNull(createdUser);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, TEST_USER_EMAIL);
        User previousUser = bookingFacade.getUserById(SECOND_ID);
        User updatedUser = bookingFacade.updateUser(user);
        assertEquals(previousUser, updatedUser);
    }

    @Test
    public void testUpdateUserWithNullName() throws Exception {
        User user = new UserImpl(SECOND_ID, null, TEST_USER_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    public void testUpdateUserWithEmptyName() throws Exception {
        User user = new UserImpl(SECOND_ID, "", TEST_USER_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    public void testUpdateUserWithNullEmail() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, null);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    public void testUpdateUserWithEmptyEmail() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, "");
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    public void testUpdateUserWithInvalidEmail() throws Exception {
        User user = new UserImpl(SECOND_ID, TEST_USER_NAME, INVALID_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    public void testUpdateUserWithInvalidId() throws Exception {
        User user = new UserImpl(INVALID_ID, TEST_USER_NAME, TEST_USER_EMAIL);
        assertNull(bookingFacade.updateUser(user));
    }

    @Test
    public void testDeleteUser() throws Exception {
        assertTrue(bookingFacade.deleteUser(SECOND_ID));
        assertNull(bookingFacade.getUserById(SECOND_ID));
    }

    @Test
    public void testDeleteUserWithInvalidId() throws Exception {
        assertFalse(bookingFacade.deleteUser(INVALID_ID));
    }

    @Test
    public void testBookTicket() throws Exception {
        assertNotNull(bookingFacade.bookTicket(FOURTH_ID, FOURTH_ID, 1, Ticket.Category.PREMIUM));
    }


}