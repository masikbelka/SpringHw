package com.epam.cdp.spring.jdbc.mapper.impl.event;

import com.epam.cdp.spring.jdbc.mapper.InsertQueryMapper;
import com.epam.cdp.spring.jdbc.mapper.UpdateQueryMapper;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.impl.EventImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class EventMapper implements InsertQueryMapper<Event>, RowMapper<Event>, UpdateQueryMapper<Event>{
    @Override
    public void mapQuery(Event entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setDate(2, new Date(entity.getDate().getTimeInMillis()));
        statement.setDouble(3, entity.getTicketPrice());
    }

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return getEvent(rs);
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        Calendar date = Calendar.getInstance();
        date.setTime(rs.getDate("event_date"));
        Event event = new EventImpl();
        event.setId(rs.getLong("event_id"));
        event.setTitle(rs.getString("event_title"));
        event.setTicketPrice(rs.getDouble("event_ticket_price"));
        event.setDate(date);
        return event;
    }

    @Override
    public Object[] mapUpdateQuery(Event entity) {
        Object[] params = new Object[4];
        params[0] = entity.getTitle();
        params[1] = new Date(entity.getDate().getTimeInMillis());
        params[2] = entity.getTicketPrice();
        params[3] = entity.getId();
        return params;
    }
}
