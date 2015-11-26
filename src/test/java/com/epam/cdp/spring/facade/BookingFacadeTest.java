package com.epam.cdp.spring.facade;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.dao.TicketDao;
import com.epam.cdp.spring.dao.UserDao;
import com.epam.cdp.spring.facade.impl.BookingFacadeImpl;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import com.epam.cdp.spring.service.EventService;
import com.epam.cdp.spring.service.TicketService;
import com.epam.cdp.spring.service.UserService;
import com.epam.cdp.spring.service.impl.EventServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class BookingFacadeTest {

    private BookingFacade bookingFacade;
    @Mock
    private EventDao eventDao;
    private EventService eventService;
    @Mock
    private UserDao userDao;
    private UserService userService;
    @Mock
    private TicketDao ticketDao;
    private TicketService ticketService;

    int validPageSize = 1;
    int validPageNum = 1;
    int inValidPageSize = 1;
    int inValidPageNum = 1;



    private User validUser;
    private User inValidUser;
    private long validUserId = 1;
    private long inValidUserId = 0;
    private String validUserName = "valid";
    private String inValidUserName = "inValid";
    private String validUserEmail = "valid";
    private String inValidUserEmail = "inValid";

    private Ticket validTicket;
    private Ticket inValidTicket;
    private long validTicketId = 1;
    private long inValidTicketId = 0;
    private Ticket.Category validCategory = Ticket.Category.STANDARD;
    private Ticket.Category inValidCategory = Ticket.Category.BAR;
    private int validPlace = 1;
    private int inValidPlace = 1;


    @Before
    public void setUp() throws Exception {
        eventService = new EventServiceImpl();

        bookingFacade = new BookingFacadeImpl(eventService, ticketService, userService);


        validUser = new UserImpl(validUserId, validUserName, validUserEmail);
        inValidUser = new UserImpl(inValidUserId, inValidUserName, inValidUserEmail);





        when(userDao.getUserById(validUserId)).thenReturn(validUser);
        when(userDao.getUserById(inValidUserId)).thenReturn(inValidUser);

    }

  /*  @Test
    public void testValidGetEventById() throws Exception {
        when(eventService.getEventById(validEventId)).thenReturn(validEvent);

        final Event eventById = bookingFacade.getEventById(validEventId);
        assertEquals(validEvent, eventById);

    }

    @Test
    public void testGetEventByZeroId() throws Exception {
        Event actualEvent = null;
        assertEquals(actualEvent, bookingFacade.getEventById(inValidEventId));
    }
*/
    @Test
    public void testGetEventsByTitle() throws Exception {

    }

    @Test
    public void testGetEventsForDay() throws Exception {

    }

    @Test
    public void testCreateEvent() throws Exception {

    }

    @Test
    public void testUpdateEvent() throws Exception {

    }

    @Test
    public void testDeleteEvent() throws Exception {

    }

    @Test
    public void testGetUserById() throws Exception {

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
    public void testBookTicket() throws Exception {

    }

    @Test
    public void testGetBookedTickets() throws Exception {

    }

    @Test
    public void testGetBookedTickets1() throws Exception {

    }

    @Test
    public void testCancelTicket() throws Exception {

    }
}