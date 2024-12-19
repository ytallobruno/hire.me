package com.bemobi.UrlShortener.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bemobi.UrlShortener.controller.models.response.PopularUrlResponse;
import com.bemobi.UrlShortener.controller.models.response.ShortenUrlResponse;
import com.bemobi.UrlShortener.service.UrlService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.RedirectView;

@ExtendWith(MockitoExtension.class)
class UrlControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
    }

    @Nested
    class ShortenUrlTests {

        @Test
        void shortenUrl() throws Exception {
            ShortenUrlResponse response = ShortenUrlResponse.builder()
                .alias("testAlias")
                .originalUrl("http://example.com")
                .hashedUrl("http://short.url/testAlias")
                .statistics(new ShortenUrlResponse.Statistics("10ms"))
                .build();

            when(urlService.shortenUrl(anyString(), anyString())).thenReturn(response);

            mockMvc.perform(put("/v1/shortener")
                    .param("url", "http://example.com")
                    .param("customAlias", "testAlias")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                    "{\"alias\":\"testAlias\",\"originalUrl\":\"http://example.com\",\"hashedUrl\":\"http://short.url/testAlias\",\"statistics\":{\"timeTaken\":\"10ms\"}}"));
        }
    }

    @Nested
    class RetrieveUrlTests {

        @Test
        void retrieveUrl() throws Exception {
            when(urlService.retrieveUrl(anyString())).thenReturn("http://example.com");

            mockMvc.perform(get("/v1/testAlias"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> {
                    RedirectView redirectView = (RedirectView) result.getModelAndView().getView();
                    assertEquals("http://example.com", redirectView.getUrl());
                });
        }
    }

    @Nested
    class GetPopularUrlsTests {

        @Test
        void getPopularUrls() throws Exception {
            PopularUrlResponse response = PopularUrlResponse.builder()
                .rank(1)
                .originalUrl("http://example.com")
                .hashedUrl("http://short.url/testAlias")
                .alias("testAlias")
                .accessCount(100L)
                .build();

            List<PopularUrlResponse> responseList = Collections.singletonList(response);

            when(urlService.getPopularUrls()).thenReturn(responseList);

            mockMvc.perform(get("/v1/popular"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                    "[{\"rank\":1,\"originalUrl\":\"http://example.com\",\"hashedUrl\":\"http://short.url/testAlias\",\"alias\":\"testAlias\",\"accessCount\":100}]"));
        }
    }


}