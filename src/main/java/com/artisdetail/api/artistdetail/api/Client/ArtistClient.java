package com.artisdetail.api.artistdetail.api.Client;



import com.artisdetail.api.artistdetail.api.Exception.CustomizingWebClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ArtistClient<T> {

    private WebClient client;

    private final String BASE_URL = "https://musicbrainz.org";

    @PostConstruct
    public void setup() {
        client = WebClient.builder().baseUrl(BASE_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }

    public Mono<T> getAs(final String s, ParameterizedTypeReference<T> clazz) {
         log.debug("Calling artist client at {}", BASE_URL);
        return client.get().uri(s).retrieve().  onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(CustomizingWebClientResponseException.ArtistClientResponseException(response.statusCode().value(), HttpStatus.valueOf(response.statusCode().value()).getReasonPhrase())))
                .bodyToMono(clazz);
    }
}
