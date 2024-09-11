package com.todoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DemoModeException extends RuntimeException {
    public DemoModeException() {
        super("Demo mode is active. Crud operations are not allowed.");
    }
}
