package com.epam.cdp.spring.facade.impl;

import com.epam.cdp.spring.exceptions.StorageModelException;
import com.epam.cdp.spring.facade.BookingFacade;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.service.EventService;
import com.epam.cdp.spring.service.TicketService;
import com.epam.cdp.spring.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class BookingFacadeImpl implements BookingFacade {
    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;

    private static final Logger LOG = LogManager.getLogger(BookingFacadeImpl.class);

    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public Event getEventById(long id) {
        return eventService.getEventById(id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    public User getUserById(long id) {
        return userService.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        return userService.createUser(user);
    }

    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    public boolean deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket bookedTicket = null;
        if (isPossibleToBook(userId, eventId, category))
            try {
                bookedTicket = ticketService.bookTicket(userId, eventId, place, category);
            } catch (StorageModelException e) {
                LOG.warn("Ticket can't to be booked(created), because: " + e.getMessage());
            }
        return bookedTicket;
    }

    private boolean isPossibleToBook(long userId, long eventId, Ticket.Category category) {
        return category != null && userService.isUserExist(userId) && eventService.isEventExist(eventId);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        return ticketService.cancelTicket(ticketId);
    }
}
