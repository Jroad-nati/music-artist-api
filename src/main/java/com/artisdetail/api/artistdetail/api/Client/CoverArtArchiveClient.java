package com.artisdetail.api.artistdetail.api.Client;


import com.artisdetail.api.artistdetail.api.Exception.CustomizingWebClientResponseException;
import com.artisdetail.api.artistdetail.api.model.down.CovertArtArchive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class CoverArtArchiveClient {

    private WebClient client;

    @Value("${cover-art.api.base-url}")
    private  String baseUrl;

    @PostConstruct
    public void setup() {
        client = WebClient.builder().baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }

    public Mono<CovertArtArchive> getAs(final String s, ParameterizedTypeReference<CovertArtArchive> clazz) {
        log.debug("Calling artist client at {}", baseUrl);
        return client.get().uri(s).retrieve().onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(CustomizingWebClientResponseException.ArtistClientResponseException(response.statusCode().value(), HttpStatus.valueOf(response.statusCode().value()).getReasonPhrase())))
                .bodyToMono(clazz);

    }
}
