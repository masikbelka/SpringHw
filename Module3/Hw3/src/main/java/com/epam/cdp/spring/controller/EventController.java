package com.epam.cdp.spring.controller;

import com.epam.cdp.spring.facade.BookingFacade;
import com.epam.cdp.spring.model.Event;
import com.epam.cdp.spring.model.impl.EventImpl;
import com.epam.cdp.spring.util.DatePropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Yurii Chukhlatyi
 */
@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private DatePropertyEditor datePropertyEditor;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Calendar.class, datePropertyEditor);
    }

    @RequestMapping(value = "/new")
    public String newEvent() {
        return "event/createEvent";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createEvent(EventImpl event) {
        Event createdEvent = bookingFacade.createEvent(event);
        return "redirect:details/" + createdEvent.getId();
    }

    @RequestMapping(value = "/details/{eventId}", method = RequestMethod.GET)
    public String showAllEvents(@PathVariable long eventId, Model model) {
        model.addAttribute("event", bookingFacade.getEventById(eventId));
        return "event/event";
    }

    @RequestMapping(value = "/forDate/{date}/{pageSize}/{pageNum}")
    public String getEventsFromDate(@PathVariable Calendar date, @PathVariable int pageSize, @PathVariable int pageNum, Model model) {
        List<Event> events = bookingFacade.getEventsForDay(date, pageSize, pageNum);
        model.addAttribute("events", events);
        return "event/events";
    }

    @RequestMapping(value = "/forTitle/{title}/{pageSize}/{pageNum}")
    public String getEventsForTitle(@PathVariable String title, @PathVariable int pageSize, @PathVariable int pageNum, Model model) {
        List<Event> events = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        model.addAttribute("events", events);
        return "event/events";
    }

    @RequestMapping(value = "/delete/{eventId}", method = RequestMethod.POST)
    public String deleteEvent(@PathVariable long eventId, Model model) {
        bookingFacade.deleteEvent(eventId);
        model.addAttribute("message", "Event deleted successfully");
        return "success";
    }

    @RequestMapping(value = "/edit/{eventId}", method = RequestMethod.GET)
    public String editEvent(@PathVariable long eventId, Model model) {
        Event event = bookingFacade.getEventById(eventId);
        model.addAttribute("event", event);
        return "event/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateEvent(EventImpl event, Model model) {
        Event updatedEvent = bookingFacade.updateEvent(event);
        model.addAttribute("event", updatedEvent);
        return "redirect:details/" + updatedEvent.getId();
    }
}
