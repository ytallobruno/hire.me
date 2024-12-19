package com.bemobi.UrlShortener.model.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String keyValue;
    private final String errCode;
    private final String description;

    public BaseException(String keyValue, String errCode, String description) {
        super(description);
        this.keyValue = keyValue;
        this.errCode = errCode;
        this.description = description;
    }
}