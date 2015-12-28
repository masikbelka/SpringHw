package com.epam.cdp.spring.controller;

import com.epam.cdp.spring.exception.BookingFacadeException;
import com.epam.cdp.spring.facade.BookingFacade;
import com.epam.cdp.spring.model.Ticket;
import com.epam.cdp.spring.model.TicketCategory;
import com.epam.cdp.spring.model.impl.TicketImpl;
import com.epam.cdp.spring.util.TicketCategoryPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Yurii Chukhlatyi
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private BookingFacade bookingFacade;

    @Autowired
    private TicketCategoryPropertyEditor ticketCategoryPropertyEditor;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(TicketCategory.class, ticketCategoryPropertyEditor);
    }

    @RequestMapping(value = "/event/{eventId}", method = RequestMethod.GET)
    public String prepareBooking(@PathVariable long eventId, Model model) {
        model.addAttribute("eventId", eventId);
        model.addAttribute("categories", TicketCategory.values());
        return "ticket/book";
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public String bookTicket(TicketImpl ticket, Model model) throws BookingFacadeException {
        Ticket bookedTicket = bookingFacade.bookTicket(ticket.getUserId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        model.addAttribute("message", bookedTicket != null ? "Ticket booked successfully" : "Failed");
        return "success";
    }

    @RequestMapping(value = "/cancel/{ticketId}", method = RequestMethod.GET)
    public String cancelTicket(@PathVariable long ticketId, Model model) {
        boolean isSuccessfully = bookingFacade.cancelTicket(ticketId);
        model.addAttribute("message", isSuccessfully ? "Ticket was cancelled" : "Filed");
        return "success";
    }
}

