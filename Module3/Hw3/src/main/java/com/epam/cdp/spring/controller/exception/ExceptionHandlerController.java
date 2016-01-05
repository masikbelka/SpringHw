package com.epam.cdp.spring.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Yurii Chukhlatyi
 */
@ControllerAdvice
public class ExceptionHandlerController {

    public static final String ERROR_VIEW = "error";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ModelAndView modelAndView = new ModelAndView(ERROR_VIEW);
        modelAndView.addObject("datetime", new Date());
        modelAndView.addObject("exception", e);
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

}
