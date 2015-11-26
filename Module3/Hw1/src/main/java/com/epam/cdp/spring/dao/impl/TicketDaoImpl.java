package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.TicketDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.TicketImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketDaoImpl implements TicketDao {

    private long lastId;

    public TicketDaoImpl() {
        this.lastId = 1;
    }

    @Autowired
    private Map<Long, Ticket> ticketStorage;

    @Override
    public boolean cancel(long ticketId) {
        return false;
    }

    @Override
    public Ticket create(long userId, long eventId, int place, Ticket.Category category) {
        long id = lastId++;
        Ticket puttedTicket = new TicketImpl(id, eventId, userId, category, place);
        ticketStorage.put(id, puttedTicket);
        return puttedTicket;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return getPage(pageSize, pageNum, getBookedTicketsByEvent(event.getId()));
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return getPage(pageSize, pageNum, getBookedTicketsByUser(user.getId()));
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(long eventId) {

        return ticketStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getEventId() == eventId)
                .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(long userId) {
        return ticketStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getEventId() == userId)
                .map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public boolean isBookingAvailable(long eventId, int place, Ticket.Category category) {
        long countBookedForPlace = getBookedTicketsByEvent(eventId).stream()
                .filter(ticket -> ticket.getCategory().equals(category) && ticket.getPlace() == place).count();
        return countBookedForPlace == 0;
    }

    private List<Ticket> getPage(int pageSize, int pageNum, List<Ticket> resultTickets) {
        int startPoint = pageSize * (pageNum - 1);
        int endPoint = pageSize * pageNum - 1;
        int size = resultTickets.size();

        if (size >= startPoint && pageNum > 0 && pageSize > 0) {
            return resultTickets.subList(startPoint, endPoint > size ? size : endPoint);
        }
        return resultTickets;
    }
}
