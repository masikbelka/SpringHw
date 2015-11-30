package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService{

    private static final Logger LOG = LogManager.getLogger(EventServiceImpl.class);

    private EventDao eventDao;

    public Event getEventById(long id) {
        Event event = null;

        if (id > 0) {
            event = eventDao.getEventById(id);
        } else {
            LOG.warn("You provide wrong id. Id must be more than zero");
        }

        return event;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>();

        if (title != null && pageSize > 0 && pageNum > 0) {
            events = eventDao.getEventsByTitle(title, pageSize, pageNum);
        } else {
            LOG.warn("You provide wrong params to method.");
        }

        return events;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>();

        if (day != null && pageSize > 0 && pageNum > 0) {
            events = eventDao.getEventsByDay(day, pageSize, pageNum);
        } else {
            LOG.warn("You provide wrong params to method.");
        }

        return events;
    }

    public Event createEvent(Event event) {
        Event createdEvent = null;

        if (isEventValid(event)) {
            createdEvent = eventDao.create(event);
        }

        return createdEvent;
    }

    public Event updateEvent(Event event) {
        Event updatedEvent = null;

        if (isEventValid(event) && event.getId() > 0) {
            updatedEvent = eventDao.update(event);
        }
        return updatedEvent;
    }

    public boolean deleteEvent(long eventId) {
        boolean isDeleted = false;
        if (eventId >= 0) {
            isDeleted = eventDao.delete(eventId);
        }
        return isDeleted;
    }

    @Override
    public boolean isEventExist(long eventId) {
        return getEventById(eventId) != null;
    }

    @Required
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    private boolean isEventValid(Event event) {
        return event!= null && event.getTitle()!= null && !event.getTitle().isEmpty() && event.getDate() != null;
    }

}
