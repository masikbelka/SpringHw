package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LogManager.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDao eventDao;

    public Event getEventById(long id) {
        Event event = null;

        if (id > 0) {
            event = eventDao.getEventById(id);
        } else {
            LOGGER.error("Invalid user id " + id);
        }

        return event;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>();

        if (title != null) {
            events = eventDao.getEventsByTitle(title, pageSize, pageNum);
        } else {
            LOGGER.error("Title can not be null!");
        }

        return events;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>();

        if (day != null) {
            events = eventDao.getEventsByDay(day, pageSize, pageNum);
        } else {
            LOGGER.error("Day can not be null!");
        }

        return events;
    }

    @Override
    public Event createEvent(Event event) {
        Event createdEvent = null;

        if (isEventValid(event)) {
            createdEvent = eventDao.create(event);
        } else {
            LOGGER.error("Invalid event " + String.valueOf(event));
        }

        return createdEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        Event updatedEvent = null;

        if (isEventValid(event) && event.getId() > 0) {
            updatedEvent = eventDao.update(event);
        } else {
            LOGGER.error("Invalid event " + String.valueOf(event));
        }
        return updatedEvent;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        boolean isDeleted = false;
        if (eventId > 0) {
            isDeleted = eventDao.delete(eventId);
        }else {
            LOGGER.error("Invalid event id " + eventId);
        }
        return isDeleted;
    }

    @Override
    public boolean isEventExist(long eventId) {
        return getEventById(eventId) != null;
    }

    private boolean isEventValid(Event event) {
        return event != null && event.getTitle() != null && !event.getTitle().isEmpty() && event.getDate() != null;
    }

}
