package com.epam.cdp.spring.model;

/**
 * Created by Yurii Chukhlatyi
 */
public enum TicketCategory {
    STANDARD(0),
    PREMIUM(1),
    BAR(2);

    private long value;

    TicketCategory(int value) {
        this.value = value;
    }

    public long value() {
        return  value;
    }

    public static TicketCategory fromValue(long value)
    {
        for (TicketCategory category : TicketCategory.values())
        {
            if (category.value == value)
            {
                return category;
            }
        }
        throw new IllegalArgumentException(String.valueOf(value));
    }
}
