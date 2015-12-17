package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.jdbc.ExtendedJDBCTemplate;
import com.epam.cdp.spring.jdbc.extractor.event.EventResultSetExtractor;
import com.epam.cdp.spring.jdbc.mapper.impl.event.EventMapper;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.impl.EventImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public class EventDaoImpl implements EventDao {

    private static final String GET_EVENT_BY_ID = "SELECT * FROM event WHERE event_id = ?";
    private static final String GET_EVENTS_BY_TITLE = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY event_id) AS RowNum, * FROM event WHERE event_title LIKE ?) "
            + "AS RowConstrainedResult WHERE RowNum > ? AND RowNum <= ?";
    private static final String GET_EVENTS_BY_DATE = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY event_date) AS RowNum, * FROM event WHERE event_date = ?) "
            + "AS RowConstrainedResult WHERE RowNum >= ? AND RowNum <= ?";
    private static final String CREATE_EVENT = "INSERT INTO event (event_title, event_date, event_ticket_price) VALUES (?, ?, ?)";
    private static final String UPDATE_EVENT = "UPDATE event SET event_title = ?, event_date = ?, event_ticket_price = ? WHERE event_id = ?";
    private static final String DELETE = "DELETE FROM event WHERE event_id = ?";

    @Autowired
    private ExtendedJDBCTemplate extendedJDBCTemplate;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EventResultSetExtractor eventResultSetExtractor;

    @Override

    public Event getEventById(long id) {
        return extendedJDBCTemplate.queryForObject(GET_EVENT_BY_ID, eventMapper, id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int startRow, int lastRow) {
        return extendedJDBCTemplate.query(GET_EVENTS_BY_TITLE, eventResultSetExtractor, title, startRow, lastRow);
    }

    @Override
    public List<Event> getEventsByDay(Calendar day, int startRow, int lastRow) {
        return extendedJDBCTemplate.query(GET_EVENTS_BY_DATE, eventResultSetExtractor, day, startRow, lastRow);
    }

    @Override
    public Event create(Event event) {
        Event createdEvent = null;
        Number generatedId = extendedJDBCTemplate.addAndReturnGeneratedKey(CREATE_EVENT, eventMapper, event);
        if (generatedId != null) {
            createdEvent = cloneEvent(event);
            createdEvent.setId(generatedId.longValue());
        }
        return createdEvent;
    }

    @Override
    public Event update(Event event) {
        return extendedJDBCTemplate.updateEntity(UPDATE_EVENT, eventMapper, event) > 0 ? event : null;
    }

    @Override
    public boolean delete(long eventId) {
        return extendedJDBCTemplate.update(DELETE, eventId) > 0;
    }

    private Event cloneEvent(Event event) {
        Calendar date = Calendar.getInstance();
        date.setTime(event.getDate().getTime());
        return new EventImpl(event.getTitle(), date, event.getTicketPrice());
    }
}
