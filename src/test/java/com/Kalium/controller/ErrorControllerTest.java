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
        // Arrange
        ErrorController errorController = new ErrorController();
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.NOT_FOUND.value());

        // Act
        String result = errorController.handleError(request);

        // Assert
        assertEquals("forward:/error-404", result);
    }

    @Test
    void handleError_withInternalServerErrorStatusCode_shouldReturnError500Page() {
        // Arrange
        ErrorController errorController = new ErrorController();
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        // Act
        String result = errorController.handleError(request);

        // Assert
        assertEquals("forward:/error-500", result);
    }

    @Test
    void handleError_withUnknownStatusCode_shouldReturnDefaultForward() {
        // Arrange
        ErrorController errorController = new ErrorController();
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(123); // Some unknown status code

        // Act
        String result = errorController.handleError(request);

        // Assert
        assertEquals("forward:/", result);
    }

    @Test
    void handle404Error_shouldReturnError404Page() {
        // Arrange
        ErrorController errorController = new ErrorController();

        // Act
        String result = errorController.handle404Error();

        // Assert
        assertEquals("error-404", result);
    }

    @Test
    void handle500Error_shouldReturnError500Page() {
        // Arrange
        ErrorController errorController = new ErrorController();

        // Act
        String result = errorController.handle500Error();

        // Assert
        assertEquals("error-500", result);
    }
}
