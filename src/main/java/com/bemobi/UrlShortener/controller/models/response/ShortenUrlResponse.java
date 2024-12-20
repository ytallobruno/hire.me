package com.bemobi.UrlShortener.controller.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortenUrlResponse {

    private String alias;
    private String originalUrl;
    private String hashedUrl;
    private Statistics statistics;

    @Data
    @AllArgsConstructor
    public static class Statistics {

        private String timeTaken;
    }

}
