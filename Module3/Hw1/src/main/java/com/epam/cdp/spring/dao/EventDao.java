package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.model.Event;

import java.util.Date;
import java.util.List;

public interface EventDao {
    Event getEventById(long id);

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsByDay(Date day, int pageSize, int pageNum);

    Event create(Event event);

    Event update(Event event);

    boolean delete(long eventId);
}
