package com.tribble.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Language was not found")
public class LanguageNotFoundException extends RuntimeException {

    public LanguageNotFoundException(String message) {
        super(message);
    }

    public LanguageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
