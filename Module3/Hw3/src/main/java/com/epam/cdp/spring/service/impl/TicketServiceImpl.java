package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.TicketDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, TicketCategory category) {
        return ticketDao.create(userId, eventId, place, category);
    }

    @Override
    public boolean isTicketBooked(long eventId, int place, TicketCategory category) {
        return ticketDao.isTicketExist(eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> tickets = new ArrayList<>();
        if (user != null && user.getId() > 0) {
            tickets = ticketDao.getBookedTickets(user, pageSize, pageNum);
        } else {
            LOGGER.error("Invalid user " + String.valueOf(user));
        }
        return tickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> tickets = new ArrayList<>();
        if (event != null && event.getId() > 0) {
            tickets = ticketDao.getBookedTickets(event, pageSize, pageNum);
        } else {
            LOGGER.error("Invalid event " + String.valueOf(event));
        }

        return tickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        boolean isCancelled = false;

        if (ticketId > 0) {
            isCancelled = ticketDao.cancel(ticketId);
        } else {
            LOGGER.error("Invalid ticket id " + ticketId);
        }

        return isCancelled;
    }

}
