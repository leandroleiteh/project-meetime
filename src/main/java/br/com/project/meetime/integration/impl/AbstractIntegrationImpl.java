package br.com.project.meetime.integration.impl;

import br.com.project.meetime.constant.GenericMessageConstant;
import br.com.project.meetime.exception.ApiIntegrationException;
import br.com.project.meetime.integration.AbstractIntegration;
import br.com.project.meetime.util.Util;
import br.com.project.meetime.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class AbstractIntegrationImpl implements AbstractIntegration {

    private final Util util;
    private final WebClient webClient;
    private final WebClientUtil webClientUtil;


    public MultiValueMap<String, String> buildDefaultHeaders(final String token) {

        var finalToken = new StringBuilder();
        finalToken.append("Bearer ").append(token);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.set(HttpHeaders.AUTHORIZATION, finalToken.toString());
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    @Override
    public Mono<ResponseEntity<Void>> post(
            final Object body,
            final String endpoint,
            final Integer apiRetryAttempts,
            final Integer apiTimeout,
            final MultiValueMap<String, String> headers) {

        long initExecutionTime = System.currentTimeMillis();
        AtomicInteger totalRetryAttemps = new AtomicInteger();

        return webClient.post()
                .uri(endpoint)
                .headers(header -> header.addAll(headers))
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(apiTimeout))
                .doOnRequest(req -> totalRetryAttemps.getAndIncrement())
                .retry(apiRetryAttempts)
                .onErrorResume(e -> {


                    throw new ApiIntegrationException(
                            endpoint,
                            totalRetryAttemps.get(),
                            (System.currentTimeMillis() - initExecutionTime),
                            webClientUtil.buildError(e),
                            util.parseToJson(body),
                            webClientUtil.buildHattpStatusError(e),
                            GenericMessageConstant.Error.INTEGRATION_API, e);

                }).cache(Duration.ofSeconds(5));
    }
}
