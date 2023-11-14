package com.Kalium.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class OrderLoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OrderLoggerInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getRequestURI().contains("/shoppingCart/checkout")) {
            logger.info("Order placed by user: {} {}", request.getRemoteUser(), request.getSession());
        }
    }
}