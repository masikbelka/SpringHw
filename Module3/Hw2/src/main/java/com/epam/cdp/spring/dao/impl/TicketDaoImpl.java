package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.TicketDao;
import com.epam.cdp.spring.jdbc.ExtendedJDBCTemplate;
import com.epam.cdp.spring.jdbc.extractor.ticket.TicketsResultSetExtractor;
import com.epam.cdp.spring.jdbc.mapper.impl.ticket.TicketMapper;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.TicketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDaoImpl implements TicketDao {

    private static final String CREATE = "INSERT INTO ticket (event_id, user_id, category_id, place) VALUES (?, ?, ?, ?)";
    private static final String GET_BOOKED_TICKETS_PAGE = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY ticket_id) AS RowNum, * FROM ticket) "
            + "AS RowConstrainedResult WHERE RowNum > ? AND RowNum <= ?";
    private static final String GET_BOOKED_TICKETS_PAGE_BY_USER = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY ticket_id) AS RowNum, * FROM ticket WHERE user_id = ?) "
            + "AS RowConstrainedResult WHERE RowNum > ? AND RowNum <= ?";
    private static final String GET_BOOKED_TICKETS_BY_EVENT = "SELECT * FROM ticket WHERE event_id = ?";
    private static final String GET_BOOKED_TICKETS_BY_USER = "SELECT * FROM ticket WHERE user_id = ?";
    private static final String IS_BOOKING_AVAILABLE = "SELECT COUNT(*) FROM ticket WHERE event_id = ? AND place = ? AND category_id = ?";


    @Autowired
    private ExtendedJDBCTemplate extendedJDBCTemplate;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketsResultSetExtractor ticketsResultSetExtractor;

    public TicketDaoImpl() {
    }

    @Override
    public boolean cancel(long ticketId) {
        return false;
    }

    @Override
    public Ticket create(long userId, long eventId, int place, TicketCategory category) {
        Ticket ticket = new TicketImpl(eventId, userId, category, place);
        Number generatedId = extendedJDBCTemplate.addAndReturnGeneratedKey(CREATE, ticketMapper, ticket);
        if (generatedId != null) {
            ticket.setId(generatedId.longValue());
        }else {
            ticket = null;
        }
        return ticket;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        int startRow = pageSize * pageNum;
        return extendedJDBCTemplate.query(GET_BOOKED_TICKETS_PAGE, ticketsResultSetExtractor, startRow, startRow + pageSize);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        int startRow = pageSize * pageNum;
        return extendedJDBCTemplate.query(GET_BOOKED_TICKETS_PAGE_BY_USER, ticketsResultSetExtractor, user.getId(), startRow, startRow + pageSize);
    }

    @Override
    public List<Ticket> getBookedTicketsByEvent(long eventId) {
        return extendedJDBCTemplate.query(GET_BOOKED_TICKETS_BY_EVENT, ticketsResultSetExtractor, eventId);
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(long userId) {
        return extendedJDBCTemplate.query(GET_BOOKED_TICKETS_BY_USER, ticketsResultSetExtractor, userId);
    }

    @Override
    public boolean isTicketExist(long eventId, int place, TicketCategory category) {
        return extendedJDBCTemplate.queryForObject(IS_BOOKING_AVAILABLE, Integer.class, eventId, place, category.value()) == 0;
    }
}
