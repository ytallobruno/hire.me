package com.bemobi.UrlShortener.model.exception;

import lombok.Getter;

@Getter
public class ShortenedUrlNotFoundException extends RuntimeException {
    private final String alias;

    public ShortenedUrlNotFoundException(String alias) {
        super("SHORTENED URL NOT FOUND");
        this.alias = alias;
    }

    public String getErrCode() {
        return "002";
    }
}