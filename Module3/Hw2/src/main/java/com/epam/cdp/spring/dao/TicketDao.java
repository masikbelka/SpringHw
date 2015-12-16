package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.User;

import java.util.List;

public interface TicketDao {

    boolean cancel(long ticketId);

    Ticket create(long userId, long eventId, int place, TicketCategory category);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTicketsByEvent(long eventId);

    List<Ticket> getBookedTicketsByUser(long userId);

    boolean isTicketExist(long eventId, int place, TicketCategory category);
}
