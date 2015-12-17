package com.epam.cdp.spring.service;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.impl.EventImpl;
import com.epam.cdp.spring.service.impl.EventServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    private Event validEvent;
    private Event invalidEvent;
    private long validEventId = 1;
    private long invalidEventId = 0;
    private String validEventTitle = "valid";
    private Calendar validEventDate = Calendar.getInstance();
    private List<Event> eventDaoList;


    @Mock
    private EventDao eventDao;
    @InjectMocks
    private EventService eventService = new EventServiceImpl();


    @Before
    public void setUp() throws Exception {
        validEvent = new EventImpl(validEventId, validEventTitle, validEventDate, 0);
        invalidEvent = null;
        when(eventDao.getEventById(validEventId)).thenReturn(validEvent);
        when(eventDao.getEventById(invalidEventId)).thenReturn(invalidEvent);
        eventDaoList = new ArrayList<>();
        eventDaoList.add(validEvent);
    
    }

    @Test
    public void testGetEventById() throws Exception {
        assertEquals(validEvent, eventService.getEventById(validEventId));
    }

    @Test
    public void testGetInvalidEventById() throws Exception {
        assertEquals(invalidEvent, eventService.getEventById(invalidEventId));
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        when(eventDao.getEventsByTitle(validEvent.getTitle(), 1, 1)).thenReturn(eventDaoList);
        assertEquals(1, eventService.getEventsByTitle(validEvent.getTitle(), 1, 1).size());
        assertEquals(validEvent, eventService.getEventsByTitle(validEvent.getTitle(), 1, 1).get(0));
    }

    @Test
    public void testGetEventsForDay() throws Exception {
        when(eventDao.getEventsByDay(validEventDate, 1, 1)).thenReturn(eventDaoList);
        assertEquals(1, eventService.getEventsForDay(validEventDate, 1, 1).size());
        assertEquals(validEvent, eventService.getEventsForDay(validEventDate, 1, 1).get(0));
    }

    @Test
    public void testCreateEvent() throws Exception {
        when(eventDao.create(validEvent)).thenReturn(validEvent);
        assertEquals(validEvent, eventService.createEvent(validEvent));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        when(eventDao.update(validEvent)).thenReturn(validEvent);
        assertEquals(validEvent, eventService.updateEvent(validEvent));
    }

    @Test
    public void testUpdateEventWhenNotExist() throws Exception {
        when(eventDao.update(invalidEvent)).thenReturn(invalidEvent);
        assertNull( eventService.updateEvent(invalidEvent));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        when(eventDao.delete(validEventId)).thenReturn(true);
        assertTrue(eventService.deleteEvent(validEventId));
    }

    @Test
    public void testDeleteEventWhenNotExist() throws Exception {
        when(eventDao.delete(invalidEventId)).thenReturn(false);
        assertFalse(eventService.deleteEvent(invalidEventId));
    }

    @Test
    public void testIsEventExist() throws Exception {
        when(eventDao.getEventById(validEventId)).thenReturn(validEvent);
        assertTrue(eventService.isEventExist(validEventId));
    }

    @Test
    public void testIsNotEventExist() throws Exception {
        when(eventDao.getEventById(invalidEventId)).thenReturn(invalidEvent);
        assertTrue(eventService.isEventExist(validEventId));
    }
}