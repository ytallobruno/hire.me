package com.bemobi.UrlShortener.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AliasUtilsTest {

    @Test
    void generateAlias() {
        String url = "http://example.com";
        String alias = AliasUtils.generateAlias(url);

        assertNotNull(alias);
        assertTrue(alias.length() >= 4);
    }

    @Test
    void generateAlias_withInvalidUrl_shouldReturnEmptyHostname() {
        String url = "invalid_url";
        String alias = AliasUtils.generateAlias(url);

        assertNotNull(alias);
        assertTrue(alias.length() >= 4);
        assertFalse(alias.endsWith("uuid")); // Verifica se o alias não termina com "uuid"
    }

    @Test
    void isValidUrl() {
        assertTrue(AliasUtils.isValidUrl("http://example.com"));
        assertTrue(AliasUtils.isValidUrl("https://example.com"));
        assertTrue(AliasUtils.isValidUrl("http://example.com/path"));
        assertFalse(AliasUtils.isValidUrl("invalid_url"));
        assertFalse(AliasUtils.isValidUrl("http://"));
    }
}