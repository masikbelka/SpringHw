package com.epam.cdp.spring.model.impl;

import com.epam.cdp.spring.model.Event;

import java.util.Date;

public class EventImpl implements Event {

    private long id;
    private String title;
    private Date date;

    public EventImpl(long id, String title, Date date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public EventImpl(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
