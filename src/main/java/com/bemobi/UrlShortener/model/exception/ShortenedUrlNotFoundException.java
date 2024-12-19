package com.bemobi.UrlShortener.model.exception;

import static com.bemobi.UrlShortener.model.errors.ErrorMessage.SHORTENED_URL_NOT_FOUND;

import lombok.Getter;

@Getter
public class ShortenedUrlNotFoundException extends BaseException {

    private final String alias;

    public ShortenedUrlNotFoundException(String alias) {
        super(alias, SHORTENED_URL_NOT_FOUND.getCode(), SHORTENED_URL_NOT_FOUND.getMessage());
        this.alias = alias;
    }
}