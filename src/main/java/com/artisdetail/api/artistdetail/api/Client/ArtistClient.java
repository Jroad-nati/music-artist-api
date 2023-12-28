package com.artisdetail.api.artistdetail.api.Client;



import com.artisdetail.api.artistdetail.api.Exception.CustomizingWebClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class ArtistClient<T> {

    private WebClient client;

    @Value("${musicbrainz.api.base-url}")
    private String baseUrl;

    @PostConstruct
    public void setup() {
        client = WebClient.builder().baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .build();
    }

    public Mono<T> getAs(final String s, ParameterizedTypeReference<T> clazz) {
         log.debug("Calling artist client at {}", baseUrl);
        return client
                 .get()
                 .uri(s)
                 .retrieve()
                 .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(CustomizingWebClientResponseException
                                .ArtistClientResponseException(response.statusCode().value(), HttpStatus.valueOf(response.statusCode().value())
                                .getReasonPhrase())))
                .bodyToMono(clazz)
                .onErrorResume(Mono::error)
                .retryWhen(Retry.backoff(3, Duration.of(2, ChronoUnit.SECONDS))
                        .onRetryExhaustedThrow((retryBackoffSpec,retrySignal) ->{
                              log.error("Retry failed");
                              return  CustomizingWebClientResponseException.CustomRetryFailedException(retrySignal.failure().getMessage());
                        }));


    }
}
