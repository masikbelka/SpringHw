package com.epam.cdp.spring.jdbc.mapper.impl.ticket;

import com.epam.cdp.spring.jdbc.mapper.InsertQueryMapper;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.impl.TicketImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yurii_Chukhlatyi on 11.08.2015.
 */
@Component
public class TicketMapper implements InsertQueryMapper<Ticket>, RowMapper<Ticket>{

    @Override
    public void mapQuery(Ticket entity, PreparedStatement statement) throws SQLException {
        statement.setLong(1, entity.getEventId());
        statement.setLong(2, entity.getUserId());
        statement.setLong(3, entity.getCategory().value());
        statement.setInt(4, entity.getPlace());
    }

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getTicket(rs);
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
