package com.bemobi.UrlShortener.service;

import com.bemobi.UrlShortener.controller.models.response.PopularUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.RetrieveUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.ShortenUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.ShortenUrlResponse.Statistics;
import com.bemobi.UrlShortener.converter.UrlToRetrieveUrlResponse;
import com.bemobi.UrlShortener.model.Url;
import com.bemobi.UrlShortener.model.exception.CustomAliasAlreadyExistsException;
import com.bemobi.UrlShortener.model.exception.InvalidUrlException;
import com.bemobi.UrlShortener.model.exception.ShortenedUrlNotFoundException;
import com.bemobi.UrlShortener.repository.UrlRepository;
import com.bemobi.UrlShortener.utils.AliasUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlToRetrieveUrlResponse urlToRetrieveUrlResponse;

    public ShortenUrlResponse shortenUrl(String url, String customAlias) {
        log.info("Shortening URL: {}", url);

        if (!AliasUtils.isValidUrl(url)) {
            throw new InvalidUrlException(url);
        }

        if (Optional.ofNullable(customAlias).map(urlRepository::findByAlias).isPresent()) {
            throw new CustomAliasAlreadyExistsException(customAlias);
        }
        long startTime = System.currentTimeMillis();

        String alias = (customAlias != null) ? customAlias : AliasUtils.generateAlias(url);
        String hashedUrl = "http://shortener/" + alias;

        Url newUrl = Url.builder()
            .originalUrl(url)
            .hashedUrl(hashedUrl)
            .alias(alias)
            .accessCount(0L)
            .build();

        urlRepository.save(newUrl);

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        return this.toShortenUrlResponse(timeTaken, newUrl);
    }

    public RetrieveUrlResponse retrieveUrl(String hashedUrl) {
        log.info("Retrieving URL for hashed URL: {}", hashedUrl);
        String baseUrl = "http://shortener/";
        if (!hashedUrl.startsWith(baseUrl)) {
            throw new ShortenedUrlNotFoundException(hashedUrl);
        }
        String alias = hashedUrl.substring(baseUrl.length());
        Url url = urlRepository.findByAlias(alias);
        if (url == null) {
            throw new ShortenedUrlNotFoundException(alias);
        }
        url.setAccessCount(url.getAccessCount() + 1);
        urlRepository.save(url);
        return urlToRetrieveUrlResponse.convert(url);
    }

    public List<PopularUrlResponse> getPopularUrls() {
        log.info("Retrieving 10 popular URLs");
        List<Url> popularUrls = urlRepository.findTop10ByOrderByAccessCountDesc();
        return IntStream.range(0, popularUrls.size())
            .mapToObj(index -> {
                Url url = popularUrls.get(index);
                return PopularUrlResponse.builder()
                    .rank(index + 1)
                    .originalUrl(url.getOriginalUrl())
                    .hashedUrl(url.getHashedUrl())
                    .alias(url.getAlias())
                    .accessCount(url.getAccessCount())
                    .build();
            })
            .collect(Collectors.toList());
    }

    private ShortenUrlResponse toShortenUrlResponse(long timeTaken, Url url) {
        Statistics statistics = new Statistics(timeTaken + "ms");
        return ShortenUrlResponse.builder()
            .alias(url.getAlias())
            .originalUrl(url.getOriginalUrl())
            .hashedUrl(url.getHashedUrl())
            .statistics(statistics)
            .build();
    }
}