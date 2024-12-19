package com.bemobi.UrlShortener.controller.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PopularUrlResponse {

    private int rank;
    private String originalUrl;
    private String hashedUrl;
    private String alias;
    private Long accessCount;
}