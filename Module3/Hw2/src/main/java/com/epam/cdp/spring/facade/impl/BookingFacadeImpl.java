package com.epam.cdp.spring.facade.impl;

import com.epam.cdp.spring.exception.BookingFacadeException;
import com.epam.cdp.spring.facade.BookingFacade;
import com.epam.cdp.spring.model.*;
import com.epam.cdp.spring.model.impl.UserAccountImpl;
import com.epam.cdp.spring.service.EventService;
import com.epam.cdp.spring.service.TicketService;
import com.epam.cdp.spring.service.UserAccountService;
import com.epam.cdp.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookingFacadeImpl implements BookingFacade {
    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;
    private UserAccountService userAccountService;

    @Autowired
    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService, UserAccountService userAccountService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.userAccountService = userAccountService;
    }

    @Override
    public Event getEventById(long id) {
        return eventService.getEventById(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long id) {
        return userService.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Transactional
    @Override
    public User createUser(User user) throws BookingFacadeException {
        User createdUser = userService.createUser(user);
        if (createdUser != null) {
            UserAccount userAccount = userAccountService.create(getEmptyUserAccountFromUser(user));
            if (userAccount == null) {
                throw new BookingFacadeException("Rollback Transaction... Unable to create User Account for user " + String.valueOf(user));
            }
        }
        return createdUser;
    }


    @Override
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @Transactional
    @Override
    public boolean deleteUser(long userId) throws BookingFacadeException {
        boolean isSuccessfullyDeleted = false;
        if (userService.deleteUser(userId)) {
            isSuccessfullyDeleted = userAccountService.deleteByUser(userId);
            if (!isSuccessfullyDeleted) {
                throw new BookingFacadeException("Rollback Transaction... Unable to remove User Account for user with ID " + userId);
            }
        }
        return isSuccessfullyDeleted;
    }

    @Override
    @Transactional
    public Ticket bookTicket(long userId, long eventId, int place, TicketCategory category) throws BookingFacadeException {
        Ticket bookedTicket = null;
        if (isPossibleToBook(userId, eventId, category) && !ticketService.isTicketBooked(eventId, place, category)) {
            Event event = eventService.getEventById(eventId);
            UserAccount userAccount = userAccountService.getUserAccountByUserId(userId);
            double calculatedFunds = userAccount.getPrepaidMoney() - event.getTicketPrice();
            if (calculatedFunds >= 0) {
                userAccount.setPrepaidMoney(calculatedFunds);
                bookedTicket = ticketService.bookTicket(userId, eventId, place, category);
                if (!userAccountService.update(userAccount)) {
                    throw new BookingFacadeException("Rollback Transaction... Unable to withdraw funds from user account");
                }
            }
        }
        return bookedTicket;
    }

    @Transactional
    @Override
    public boolean addFunds(long userAccountId, double funds) throws BookingFacadeException {
        validateFunds(funds);
        UserAccount userAccount = userAccountService.getUserAccountById(userAccountId);
        if (userAccount != null) {
            userAccount.setPrepaidMoney(userAccount.getPrepaidMoney() + funds);
            if (!userAccountService.update(userAccount)) {
                throw new BookingFacadeException("Rollback Transaction... Unable to add funds to user account");
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean withdrawFunds(long userAccountId, double funds) throws BookingFacadeException {
        validateFunds(funds);
        boolean isWithdrawSuccessful = false;
        UserAccount userAccount = userAccountService.getUserAccountById(userAccountId);
        if (userAccount != null) {
            double calculatedFunds = userAccount.getPrepaidMoney() - funds;
            if (funds >= 0) {
                userAccount.setPrepaidMoney(calculatedFunds);
                if(!userAccountService.update(userAccount)) {
                    throw new BookingFacadeException("Rollback Transaction... Unable to withdraw funds from user account");
                }
                isWithdrawSuccessful = true;
            }
        }
        return isWithdrawSuccessful;
    }

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        return userAccountService.getUserAccountById(userAccountId);
    }

    @Override
    public UserAccount getUserAccountByUser(long userId) {
        return userAccountService.getUserAccountByUserId(userId);
    }

    private void validateFunds(double funds) throws BookingFacadeException {
        if (funds < 0) {
            throw new BookingFacadeException("Funds can't be less than 0. Received value is " + funds);
        }
    }

    private boolean isPossibleToBook(long userId, long eventId, TicketCategory category) {
        boolean isCategoryValid = category != null;
        boolean isUserExist = userService.isUserExist(userId);
        boolean isUserAccountExist = userAccountService.isUserAccountForUserExist(userId);
        boolean isEventExist = eventService.isEventExist(eventId);
        return isCategoryValid && isUserExist && isUserAccountExist && isEventExist;
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

    private UserAccount getEmptyUserAccountFromUser(User user) {
        UserAccount userAccount = new UserAccountImpl();
        userAccount.setUserId(user.getId());
        userAccount.setPrepaidMoney(0);
        return userAccount;
    }
}
