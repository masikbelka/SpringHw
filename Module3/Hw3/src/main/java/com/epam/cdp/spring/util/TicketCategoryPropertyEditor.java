package com.epam.cdp.spring.util;

import com.epam.cdp.spring.model.TicketCategory;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by Yurii Chukhlatyi
 */
@Component
public class TicketCategoryPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(TicketCategory.fromValue(Long.valueOf(text)));
    }
}
