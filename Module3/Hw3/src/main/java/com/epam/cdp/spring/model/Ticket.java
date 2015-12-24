package com.epam.cdp.spring.model;

/**
 * Created by maksym_govorischev.
 */
public interface Ticket {
    /**
     * Ticket Id. UNIQUE.
     *
     * @return Ticket Id.
     */
    long getId();

    void setId(long id);

    long getEventId();

    void setEventId(long eventId);

    long getUserId();

    void setUserId(long userId);

    TicketCategory getCategory();

    void setCategory(TicketCategory category);

    int getPlace();

    void setPlace(int place);

}
