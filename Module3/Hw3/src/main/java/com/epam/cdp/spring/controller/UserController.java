package com.epam.cdp.spring.controller;

import com.epam.cdp.spring.exception.BookingFacadeException;
import com.epam.cdp.spring.facade.BookingFacade;
import com.epam.cdp.spring.model.User;
import com.epam.cdp.spring.model.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Yurii Chukhlatyi
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private BookingFacade bookingFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String registerUser(UserImpl user) throws BookingFacadeException {
        User createdUser = bookingFacade.createUser(user);
        return "redirect:profile/" + createdUser.getId();
    }

    @RequestMapping(value = "/profile/id/{userId}", method = RequestMethod.GET)
    public String getUser(@PathVariable long userId, Model model) {
        User user = bookingFacade.getUserById(userId);
        model.addAttribute("user", user);
        return "user/userProfile";
    }

    @RequestMapping(value = "/profile/email/{email}", method = RequestMethod.GET)
    public String getUserByEmail(@PathVariable String email, Model model) {
        User user = bookingFacade.getUserByEmail(email);
        model.addAttribute("user", user);
        return "user/userProfile";
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable long userId, Model model) throws BookingFacadeException {
        bookingFacade.deleteUser(userId);
        model.addAttribute("message", "User deleted successfully");
        return "success";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String userRegistration() {
        return "user/registration";
    }

    @RequestMapping(value = "/byName/{name}/{pageSize}/{pageNum}", method = RequestMethod.GET)
    public String getUsers(@PathVariable String name, @PathVariable int pageSize, @PathVariable int pageNum, Model model) {
        List<User> users = bookingFacade.getUsersByName(name, pageSize, pageNum);
        model.addAttribute("users", users);
        return "user/users";
    }

    @RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
    public String editEvent(@PathVariable long userId, Model model) {
        User user = bookingFacade.getUserById(userId);
        model.addAttribute("user", user);
        return "user/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateEvent(UserImpl user, Model model) {
        User updatedUser = bookingFacade.updateUser(user);
        model.addAttribute("event", updatedUser);
        return "redirect:profile/id/" + updatedUser.getId();
    }
}


