package br.com.project.meetime.integration;


import br.com.project.meetime.enums.ActionEnum;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface HubSpotIntegration {

    Mono<ResponseEntity<Void>> post(
            final ActionEnum actionEnum,
            final Object body,
            final String endpoint,
            final String token);
}
