package com.epam.cdp.spring.controller.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Yurii Chukhlatyi
 */
public class ExceptionHandlerController {

    public static final String ERROR_VIEW = "error";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView handleException(HttpServletRequest request, Exception e) {
        ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);
        modelAndView.addObject("datetime", new Date());
        modelAndView.addObject("exception", e);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

}
