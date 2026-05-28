package com.store.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class UserExistException extends ApiException {
    public UserExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
