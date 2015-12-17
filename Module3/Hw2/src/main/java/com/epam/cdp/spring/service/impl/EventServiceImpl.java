package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.service.EventService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
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
        if (title != null && isPageSizeAndPageNumValid(pageSize, pageNum)) {
            String formattedTitle = "%" + title.trim() + "%";
            events = eventDao.getEventsByTitle(formattedTitle, getStartRow(pageSize, pageNum), getLastRow(pageSize, pageNum));
        } else {
            LOGGER.error("Title can not be null!");
        }
        return events;
    }

    @Override
    public List<Event> getEventsForDay(Calendar day, int pageSize, int pageNum) {
        List<Event> events = new ArrayList<>();
        if (day != null && isPageSizeAndPageNumValid(pageSize, pageNum)) {
            events = eventDao.getEventsByDay(day, getStartRow(pageSize, pageNum), getLastRow(pageSize, pageNum));
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
        } else {
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

    private int getStartRow(int pageSize, int pageNum) {
        return pageSize * (pageNum - 1);
    }

    private int getLastRow(int pageSize, int pageNum) {
        return pageSize * pageNum;
    }

    private boolean isPageSizeAndPageNumValid(int pageSize, int pageNum) {
        boolean isValid = true;
        if (pageSize < 1 || pageNum < 1) {
            LOGGER.error(String.format("Invalid parameters {pageSize:%d} {pageNum:%d}", pageSize, pageNum));
            isValid = false;
        }
        return isValid;
    }

}
