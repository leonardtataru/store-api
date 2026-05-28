package com.store.api.exceptions;

import org.springframework.http.HttpStatus;

public class WrongUsernameOrPassword extends RuntimeException {
    private final HttpStatus status;

    public WrongUsernameOrPassword(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
