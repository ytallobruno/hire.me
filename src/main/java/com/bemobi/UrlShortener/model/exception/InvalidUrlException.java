package com.bemobi.UrlShortener.model.exception;

import static com.bemobi.UrlShortener.model.errors.ErrorMessage.INVALID_URL_FORMAT;

import lombok.Getter;

@Getter
public class InvalidUrlException extends BaseException {

    private final String url;

    public InvalidUrlException(String url) {
        super(url, INVALID_URL_FORMAT.getCode(), INVALID_URL_FORMAT.getMessage());
        this.url = url;
    }
}