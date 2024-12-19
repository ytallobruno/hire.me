package com.bemobi.UrlShortener.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class HireMeConfig {

    @Value(value = "${app.base-url}")
    private String appBaseUrl;

}