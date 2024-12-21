package com.bemobi.UrlShortener.converter;

import com.bemobi.UrlShortener.controller.models.response.RetrieveUrlResponse;
import com.bemobi.UrlShortener.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlToRetrieveUrlResponse implements Converter<Url, RetrieveUrlResponse> {

    @Override
    public RetrieveUrlResponse convert(Url url) {
        return RetrieveUrlResponse.builder()
            .alias(url.getAlias())
            .originalUrl(url.getOriginalUrl())
            .hashedUrl(url.getHashedUrl())
            .accessCount(url.getAccessCount())
            .build();
    }
}
