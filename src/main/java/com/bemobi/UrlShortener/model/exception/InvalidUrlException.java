package com.bemobi.UrlShortener.model.exception;

import lombok.Getter;

@Getter
public class InvalidUrlException extends RuntimeException {
    private final String url;

    public InvalidUrlException(String url) {
        super("INVALID URL FORMAT");
        this.url = url;
    }

    public String getErrCode() {
        return "003";
    }
}