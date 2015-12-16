package com.epam.cdp.spring.jdbc.extractor.event;

import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.impl.EventImpl;
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
public class EventResultSetExtractor implements ResultSetExtractor<List<Event>> {

    @Override
    public List<Event> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Event> events = new ArrayList<>();
        while (resultSet.next()) {
            Event event = getEvent(resultSet);
            events.add(event);
        }
        return events;
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        Event event = new EventImpl();
        event.setId(rs.getInt("event_id"));
        event.setTitle(rs.getString("event_title"));
        event.setDate(rs.getDate("event_date"));
        event.setTicketPrice(rs.getInt("event_ticket_price"));
        return event;
    }
}
