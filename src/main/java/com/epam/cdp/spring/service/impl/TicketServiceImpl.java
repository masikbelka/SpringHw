package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.TicketDao;
import com.epam.cdp.spring.exceptions.StorageModelException;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOG = LogManager.getLogger(TicketServiceImpl.class);

    private TicketDao ticketDao;

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) throws StorageModelException {
        Ticket createdTicket;

        if (ticketDao.isBookingAvailable(eventId, place, category)) {
            createdTicket = ticketDao.create(userId, eventId, place, category);
        } else {
            String message = "Can't book(create) ticket.";
            LOG.warn(message);
            throw new StorageModelException(message);
        }

        return createdTicket;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> tickets = new ArrayList<>();
        if (user != null && user.getId() > 0) {
            tickets = ticketDao.getBookedTickets(user, pageSize, pageNum);
        }

        return tickets;
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> tickets = new ArrayList<>();
        if (event != null && event.getId() > 0) {
            tickets = ticketDao.getBookedTickets(event, pageSize, pageNum);
        }

        return tickets;
    }

    public boolean cancelTicket(long ticketId) {
        boolean isCancelled = false;

        if (ticketId >= 0) {
            isCancelled = ticketDao.cancel(ticketId);
        } else {
            LOG.info("Provided ticketId[" + ticketId + "] isn't valid");
        }

        return isCancelled;
    }

    @Required
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
