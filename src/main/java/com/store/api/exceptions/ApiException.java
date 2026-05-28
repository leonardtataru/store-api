package com.store.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public sealed class ApiException extends RuntimeException
        permits NoProductException, ProductExistException,
        UserExistException, WrongUsernameOrPassword, ExpiredToken {
    private final HttpStatus status;

    protected ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
