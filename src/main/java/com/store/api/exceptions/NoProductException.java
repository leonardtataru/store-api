package com.store.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class NoProductException extends ApiException {
    public NoProductException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
