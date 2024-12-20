package com.bemobi.UrlShortener.controller;

import com.bemobi.UrlShortener.controller.models.response.PopularUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.RetrieveUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.ShortenUrlResponse;
import com.bemobi.UrlShortener.service.UrlService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/v1")
public class UrlController {

    private final UrlService urlService;

    @PutMapping("/shortener")
    public ShortenUrlResponse shortenUrl(@RequestParam String url, @RequestParam(required = false) String customAlias) {
        return urlService.shortenUrl(url, customAlias);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> retrieveUrl(@RequestParam String alias) {
        RetrieveUrlResponse retrieveUrlResponse = urlService.retrieveUrl(alias);
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", retrieveUrlResponse.getOriginalUrl())
            .location(URI.create(retrieveUrlResponse.getOriginalUrl()))
            .build();
    }

    @GetMapping("/popular")
    public List<PopularUrlResponse> getPopularUrls() {
        return urlService.getPopularUrls();
    }

}
