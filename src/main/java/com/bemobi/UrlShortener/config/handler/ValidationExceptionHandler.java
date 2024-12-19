package com.bemobi.UrlShortener.config.handler;

import com.bemobi.UrlShortener.model.exception.CustomAliasAlreadyExistsException;
import com.bemobi.UrlShortener.model.exception.InvalidUrlException;
import com.bemobi.UrlShortener.model.exception.ShortenedUrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(CustomAliasAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCustomAliasAlreadyExistsException(CustomAliasAlreadyExistsException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("alias", ex.getAlias());
        errorResponse.put("err_code", ex.getErrCode());
        errorResponse.put("description", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShortenedUrlNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleShortenedUrlNotFoundException(ShortenedUrlNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("alias", ex.getAlias());
        errorResponse.put("err_code", ex.getErrCode());
        errorResponse.put("description", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUrlException(InvalidUrlException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("url", ex.getUrl());
        errorResponse.put("err_code", ex.getErrCode());
        errorResponse.put("description", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}