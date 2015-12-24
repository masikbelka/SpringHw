package com.epam.cdp.spring.model.impl;

import com.epam.cdp.spring.model.Event;

import java.util.Calendar;

public class EventImpl implements Event {

    private long id;
    private String title;
    private Calendar date;
    private double ticketPrice;

    public EventImpl(long id, String title, Calendar date, double ticketPrice) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public EventImpl(String title, Calendar date, double ticketPrice) {
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public EventImpl() {
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
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Calendar getDate() {
        return date;
    }

    @Override
    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventImpl event = (EventImpl) o;

        if (getId() != event.getId()) return false;
        if (Double.compare(event.getTicketPrice(), getTicketPrice()) != 0) return false;
        if (!getTitle().equals(event.getTitle())) return false;
        return getDate().equals(event.getDate());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getDate().hashCode();
        temp = Double.doubleToLongBits(getTicketPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
