package br.com.project.meetime.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

public interface AbstractIntegration {

    MultiValueMap<String, String> buildDefaultHeaders(final String token);

    Mono<ResponseEntity<Void>> post(
            final Object body,
            final String endpoint,
            final Integer apiRetryAttempts,
            final Integer apiTimeout,
            final MultiValueMap<String, String> headers);
}
