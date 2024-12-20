package com.bemobi.UrlShortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bemobi.UrlShortener.config.HireMeConfig;
import com.bemobi.UrlShortener.controller.models.response.PopularUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.ShortenUrlResponse;
import com.bemobi.UrlShortener.model.Url;
import com.bemobi.UrlShortener.model.exception.CustomAliasAlreadyExistsException;
import com.bemobi.UrlShortener.model.exception.InvalidUrlException;
import com.bemobi.UrlShortener.model.exception.ShortenedUrlNotFoundException;
import com.bemobi.UrlShortener.repository.UrlRepository;
import com.bemobi.UrlShortener.utils.AliasUtils;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private HireMeConfig hireMeConfig;

    @InjectMocks
    private UrlService urlService;

    @Nested
    class ShortenUrlTests {

        @Test
        void withValidUrlAndNoCustomAlias_shouldReturnShortenUrlResponse() {
            String url = "http://example.com";

            try (MockedStatic<AliasUtils> aliasUtilsMockedStatic = mockStatic(AliasUtils.class)) {
                aliasUtilsMockedStatic.when(() -> AliasUtils.isValidUrl(url)).thenReturn(true);

                String generatedAlias = AliasUtils.generateAlias(url);
                aliasUtilsMockedStatic.when(() -> AliasUtils.generateAlias(url)).thenReturn(generatedAlias);

                Url newUrl = Url.builder()
                    .originalUrl(url)
                    .hashedUrl("http://short.url/v1/" + generatedAlias)
                    .accessCount(0L)
                    .build();

                when(hireMeConfig.getAppBaseUrl()).thenReturn("http://short.url");
                when(urlRepository.save(any(Url.class))).thenReturn(newUrl);

                ShortenUrlResponse response = urlService.shortenUrl(url, null);

                assertNotNull(response);
                assertEquals(generatedAlias, response.getAlias());
                assertEquals(url, response.getOriginalUrl());
                assertEquals(newUrl.getHashedUrl(), response.getHashedUrl());
                assertNotNull(response.getStatistics());
            }
        }

        @Test
        void withValidUrlAndCustomAlias_shouldReturnShortenUrlResponse() {
            String url = "http://example.com";
            String customAlias = "customAlias";
            String hashedUrl = "http://short.url/v1/" + customAlias;

            try (MockedStatic<AliasUtils> aliasUtilsMockedStatic = mockStatic(AliasUtils.class)) {
                aliasUtilsMockedStatic.when(() -> AliasUtils.isValidUrl(url)).thenReturn(true);

                Url newUrl = Url.builder()
                    .originalUrl(url)
                    .hashedUrl(hashedUrl)
                    .alias(customAlias)
                    .accessCount(0L)
                    .build();

                when(hireMeConfig.getAppBaseUrl()).thenReturn("http://short.url");
                when(urlRepository.save(any(Url.class))).thenReturn(newUrl);

                ShortenUrlResponse response = urlService.shortenUrl(url, customAlias);

                assertNotNull(response);
                assertEquals(customAlias, response.getAlias());
                assertEquals(url, response.getOriginalUrl());
                assertEquals(hashedUrl, response.getHashedUrl());
                assertNotNull(response.getStatistics());
            }
        }

        @Test
        void withInvalidUrl_shouldThrowInvalidUrlException() {
            String url = "invalid_url";

            try (MockedStatic<AliasUtils> aliasUtilsMockedStatic = mockStatic(AliasUtils.class)) {
                aliasUtilsMockedStatic.when(() -> AliasUtils.isValidUrl(url)).thenReturn(false);

                assertThrows(InvalidUrlException.class, () -> urlService.shortenUrl(url, null));
            }
        }

        @Test
        void withCustomAliasAlreadyExists_shouldThrowCustomAliasAlreadyExistsException() {
            String url = "http://example.com";
            String customAlias = "existingAlias";

            try (MockedStatic<AliasUtils> aliasUtilsMockedStatic = mockStatic(AliasUtils.class)) {
                aliasUtilsMockedStatic.when(() -> AliasUtils.isValidUrl(url)).thenReturn(true);
                when(urlRepository.findByAlias(customAlias)).thenReturn(new Url());

                assertThrows(CustomAliasAlreadyExistsException.class, () -> urlService.shortenUrl(url, customAlias));
            }
        }
    }

    @Nested
    class RetrieveUrlTests {

        @Test
        void withExistingAlias_shouldReturnOriginalUrl() {
            String alias = "testAlias";
            String originalUrl = "http://example.com";

            Url url = Url.builder()
                .originalUrl(originalUrl)
                .alias(alias)
                .accessCount(0L)
                .build();

            when(urlRepository.findByAlias(alias)).thenReturn(url);

            String retrievedUrl = urlService.retrieveUrl(alias);

            assertEquals(originalUrl, retrievedUrl);
            assertEquals(1L, url.getAccessCount());
            verify(urlRepository, times(1)).save(url);
        }

        @Test
        void withNonExistingAlias_shouldThrowShortenedUrlNotFoundException() {
            String alias = "nonExistingAlias";

            when(urlRepository.findByAlias(alias)).thenReturn(null);

            assertThrows(ShortenedUrlNotFoundException.class, () -> urlService.retrieveUrl(alias));
        }
    }

    @Nested
    class GetPopularUrlsTests {

        @Test
        void shouldReturnListOfPopularUrlResponses() {
            List<Url> urls = IntStream.range(0, 10)
                .mapToObj(i -> Url.builder()
                    .originalUrl("http://example.com/" + i)
                    .hashedUrl("http://short.url/testAlias" + i)
                    .alias("testAlias" + i)
                    .accessCount((long) (100 - i))
                    .build())
                .collect(Collectors.toList());

            when(urlRepository.findTop10ByOrderByAccessCountDesc()).thenReturn(urls);

            List<PopularUrlResponse> popularUrls = urlService.getPopularUrls();

            assertNotNull(popularUrls);
            assertEquals(10, popularUrls.size());
            for (int i = 0; i < 10; i++) {
                PopularUrlResponse response = popularUrls.get(i);
                assertEquals(i + 1, response.getRank());
                assertEquals(urls.get(i).getOriginalUrl(), response.getOriginalUrl());
                assertEquals(urls.get(i).getHashedUrl(), response.getHashedUrl());
                assertEquals(urls.get(i).getAlias(), response.getAlias());
                assertEquals(urls.get(i).getAccessCount(), response.getAccessCount());
            }
        }
    }
}