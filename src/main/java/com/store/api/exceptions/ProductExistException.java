package com.store.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class ProductExistException extends ApiException {

    public ProductExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
