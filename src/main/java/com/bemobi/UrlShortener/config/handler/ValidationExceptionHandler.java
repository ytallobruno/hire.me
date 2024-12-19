package com.bemobi.UrlShortener.config.handler;

import com.bemobi.UrlShortener.model.exception.BaseException;
import com.bemobi.UrlShortener.model.exception.CustomAliasAlreadyExistsException;
import com.bemobi.UrlShortener.model.exception.InvalidUrlException;
import com.bemobi.UrlShortener.model.exception.ShortenedUrlNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(CustomAliasAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCustomAliasAlreadyExistsException(CustomAliasAlreadyExistsException ex) {
        return buildErrorResponse("alias", ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShortenedUrlNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleShortenedUrlNotFoundException(ShortenedUrlNotFoundException ex) {
        return buildErrorResponse("alias", ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUrlException(InvalidUrlException ex) {
        return buildErrorResponse("url", ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String keyName, BaseException ex, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(keyName, ex.getKeyValue());
        errorResponse.put("err_code", ex.getErrCode());
        errorResponse.put("description", ex.getDescription());
        return new ResponseEntity<>(errorResponse, status);
    }
}