package com.epam.cdp.spring.jdbc.extractor.ticket;

import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.impl.TicketImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class TicketsResultSetExtractor implements ResultSetExtractor<List<Ticket>> {
    @Override
    public List<Ticket> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Ticket> tickets = new ArrayList<>();
        while (resultSet.next()) {
            Ticket ticket = getTicket(resultSet);
            tickets.add(ticket);
        }
        return tickets;
    }

    private Ticket getTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new TicketImpl();
        ticket.setId(rs.getLong("ticket_id"));
        ticket.setEventId(rs.getLong("event_id"));
        ticket.setUserId(rs.getLong("user_id"));
        ticket.setCategory(TicketCategory.fromValue(rs.getLong("ticket_category_id")));
        ticket.setPlace(rs.getInt("ticket_id"));
        return ticket;
    }
}
