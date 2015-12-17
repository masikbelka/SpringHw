package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.model.Event;

import java.util.Calendar;
import java.util.List;

public interface EventDao {
    Event getEventById(long id);

    List<Event> getEventsByTitle(String title, int startRow, int lastRow);

    List<Event> getEventsByDay(Calendar day, int startRow, int lastRow);

    Event create(Event event);

    Event update(Event event);

    boolean delete(long eventId);
}
