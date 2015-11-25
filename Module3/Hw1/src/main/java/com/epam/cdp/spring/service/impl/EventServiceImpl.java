package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService{

    private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);

    public Event getEventById(long id) {
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    public Event createEvent(Event event) {
        return null;
    }

    public Event updateEvent(Event event) {
        return null;
    }

    public boolean deleteEvent(long eventId) {
        return false;
    }
}
