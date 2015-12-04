package com.epam.cdp.spring.dao.impl;

import com.epam.cdp.spring.dao.EventDao;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.impl.EventImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventDaoImpl implements EventDao {

    private long lastId;

    @Resource
    private Map<Long, Event> eventStorage;

    private void initLastId(){
        if(eventStorage != null && !eventStorage.isEmpty()){
            this.lastId = eventStorage.size();
        }
    }

    @Override
    public Event getEventById(long id) {
        return eventStorage.get(id);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        final List<Event> resultEvents = eventStorage.values().stream()
                .filter(value -> StringUtils.containsIgnoreCase(value.getTitle(),title))
                .collect(Collectors.toList());

        return getPage(pageSize, pageNum, resultEvents);
    }

    private List<Event> getPage(int pageSize, int pageNum, List<Event> resultEvents) {
        int startPoint = pageSize * (pageNum - 1);
        int endPoint = pageSize * pageNum;
        int size = resultEvents.size();

        if (size >= startPoint && pageNum > 0 && pageSize > 0) {
            return resultEvents.subList(startPoint, endPoint > size ? size : endPoint);
        }
        return resultEvents;
    }

    @Override
    public List<Event> getEventsByDay(Date day, int pageSize, int pageNum) {
        final List<Event> resultEvents = eventStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getDate().equals(day))
                .map(Map.Entry::getValue).collect(Collectors.toList());

        return getPage(pageSize, pageNum, resultEvents);
    }

    @Override
    public Event create(Event event) {
        long id = ++lastId;
        Event puttedEvent = new EventImpl(id, event.getTitle(), event.getDate());
        eventStorage.put(id, puttedEvent);
        return puttedEvent;
    }

    @Override
    public Event update(Event event) {
        return eventStorage.replace(event.getId(), event);
    }

    @Override
    public boolean delete(long eventId) {
        return eventStorage.remove(eventId) != null;
    }
}
