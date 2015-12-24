package com.epam.cdp.spring.model;

import java.util.Calendar;

/**
 * Created by maksym_govorischev.
 */
public interface Event {
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    long getId();
    void setId(long id);
    String getTitle();
    void setTitle(String title);
    Calendar getDate();
    void setDate(Calendar date);

    double getTicketPrice();

    void setTicketPrice(double ticketPrice);
}
