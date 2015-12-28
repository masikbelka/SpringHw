package com.epam.cdp.spring.util;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class DatePropertyEditor extends PropertyEditorSupport {
    private SimpleDateFormat simpleDateFormat;

    public DatePropertyEditor(){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public String getFormattedDate(Calendar calendar) {
        return simpleDateFormat.format(calendar);
    }

    public Calendar parseDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(date));
        return calendar;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(parseDate(text));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
