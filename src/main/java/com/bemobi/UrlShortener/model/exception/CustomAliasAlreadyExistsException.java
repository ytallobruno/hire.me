package com.bemobi.UrlShortener.model.exception;

import static com.bemobi.UrlShortener.model.errors.ErrorMessage.CUSTOM_ALIAS_ALREADY_EXISTS;

import lombok.Getter;

@Getter
public class CustomAliasAlreadyExistsException extends BaseException {

    private final String alias;

    public CustomAliasAlreadyExistsException(String alias) {
        super(alias, CUSTOM_ALIAS_ALREADY_EXISTS.getCode(), CUSTOM_ALIAS_ALREADY_EXISTS.getMessage());
        this.alias = alias;
    }
}
