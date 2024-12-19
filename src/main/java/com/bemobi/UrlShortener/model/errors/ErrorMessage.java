package com.bemobi.UrlShortener.model.errors;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class ErrorMessage implements Serializable {

    public static final ErrorMessage CUSTOM_ALIAS_ALREADY_EXISTS = new ErrorMessage("001",
        "CUSTOM ALIAS ALREADY EXISTS");
    public static final ErrorMessage SHORTENED_URL_NOT_FOUND = new ErrorMessage("002", "SHORTENED URL NOT FOUND");
    public static final ErrorMessage INVALID_URL_FORMAT = new ErrorMessage("003", "INVALID URL FORMAT");

    private final String code;
    private final String message;

    private ErrorMessage(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
