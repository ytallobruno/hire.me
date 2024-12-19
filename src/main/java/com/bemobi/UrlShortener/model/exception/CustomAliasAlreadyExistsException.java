package com.bemobi.UrlShortener.model.exception;

import lombok.Getter;

@Getter
public class CustomAliasAlreadyExistsException extends RuntimeException {
    private final String alias;

    public CustomAliasAlreadyExistsException(String alias) {
        super("CUSTOM ALIAS ALREADY EXISTS");
        this.alias = alias;
    }

    public String getErrCode() {
        return "001";
    }
}
