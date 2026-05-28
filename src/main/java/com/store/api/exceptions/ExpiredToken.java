package com.store.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class ExpiredToken extends ApiException {
    public ExpiredToken(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
