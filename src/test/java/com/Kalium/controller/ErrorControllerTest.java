package com.Kalium.controller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorControllerTest {

    @Test
    void handleError_withNotFoundStatusCode_shouldReturnError404Page() {
        ErrorController errorController = new ErrorController();
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.NOT_FOUND.value());

        String result = errorController.handleError(request);

        assertEquals("forward:/error-404", result);
    }

    @Test
    void handleError_withInternalServerErrorStatusCode_shouldReturnError500Page() {
        ErrorController errorController = new ErrorController();
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        String result = errorController.handleError(request);

        assertEquals("forward:/error-500", result);
    }

    @Test
    void handleError_withUnknownStatusCode_shouldReturnDefaultForward() {
        ErrorController errorController = new ErrorController();
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(123);

        String result = errorController.handleError(request);

        assertEquals("forward:/", result);
    }

    @Test
    void handle404Error_shouldReturnError404Page() {
        ErrorController errorController = new ErrorController();

        String result = errorController.handle404Error();

        assertEquals("error-404", result);
    }

    @Test
    void handle500Error_shouldReturnError500Page() {
        ErrorController errorController = new ErrorController();

        String result = errorController.handle500Error();

        assertEquals("error-500", result);
    }
}
