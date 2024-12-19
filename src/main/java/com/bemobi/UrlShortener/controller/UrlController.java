package com.bemobi.UrlShortener.controller;

import com.bemobi.UrlShortener.controller.models.response.PopularUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.ShortenUrlResponse;
import com.bemobi.UrlShortener.service.UrlService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/{alias}")
    public RedirectView retrieveUrl(@PathVariable String alias) {
        String originalUrl = urlService.retrieveUrl(alias);
        return new RedirectView(originalUrl);
    }

    @GetMapping("/popular")
    public List<PopularUrlResponse> getPopularUrls() {
        return urlService.getPopularUrls();
    }

}
