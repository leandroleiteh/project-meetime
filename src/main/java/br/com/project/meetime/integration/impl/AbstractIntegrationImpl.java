package br.com.project.meetime.integration.impl;

import br.com.project.meetime.constant.GenericMessageConstant;
import br.com.project.meetime.exception.ApiException;
import br.com.project.meetime.exception.ApiIntegrationException;
import br.com.project.meetime.integration.AbstractIntegration;
import br.com.project.meetime.util.Util;
import br.com.project.meetime.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
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
                .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, response -> {

                    var retryAfter = response.headers().asHttpHeaders().getFirst("Retry-After");
                    long delay = retryAfter != null ? Long.parseLong(retryAfter) : 5L;

                    log.warn("Rate limit. Aguardando {} segundos...", delay);
                    return Mono.delay(Duration.ofSeconds(delay))
                            .then(Mono.error(new ApiException("Rate limit exceeded",
                                    HttpStatusCode.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()))));
                })
                .toBodilessEntity()
                .timeout(Duration.ofSeconds(apiTimeout))
                .doOnEach(signal -> {
                    if (signal.isOnError()) {
                        totalRetryAttemps.incrementAndGet();
                    }
                })
                .retryWhen(reactor.util.retry.Retry.backoff(apiRetryAttempts, Duration.ofSeconds(2))
                        .filter(throwable -> throwable instanceof ApiException)
                        .onRetryExhaustedThrow((retryBackoffSpec, signal) -> signal.failure()))
                .doOnError(e -> {
                    log.error("Erro após {} tentativas em {}: {}", totalRetryAttemps.get(), endpoint, e.getMessage());
                })
                .onErrorResume(e -> Mono.error(new ApiIntegrationException(
                        endpoint,
                        totalRetryAttemps.get(),
                        (System.currentTimeMillis() - initExecutionTime),
                        webClientUtil.buildError(e),
                        util.parseToJson(body),
                        webClientUtil.buildHattpStatusError(e),
                        GenericMessageConstant.Error.INTEGRATION_API, e))
                ).cache(Duration.ofSeconds(5));
    }
}
