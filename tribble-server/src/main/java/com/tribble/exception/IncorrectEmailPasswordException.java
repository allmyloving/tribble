package com.tribble.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Incorrect email or password")
public class IncorrectEmailPasswordException extends RuntimeException {

    public IncorrectEmailPasswordException () {
        super();
    }

    public IncorrectEmailPasswordException (String message) {
        super(message);
    }

    public IncorrectEmailPasswordException (String message, Throwable cause) {
        super(message, cause);
    }
}
