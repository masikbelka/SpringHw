package com.epam.cdp.spring.model.impl;

import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;

public class TicketImpl implements Ticket {

    private long id;
    private long eventId;
    private long userId;
    private TicketCategory category;
    private int place;

    public TicketImpl(long id, long eventId, long userId, TicketCategory category, int place) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    public TicketImpl(long eventId, long userId, TicketCategory category, int place) {
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    public TicketImpl() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public TicketCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", category=" + category +
                ", place=" + place +
                '}';
    }
}
