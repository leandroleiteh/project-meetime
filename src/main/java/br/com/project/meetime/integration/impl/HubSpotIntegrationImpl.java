package br.com.project.meetime.integration.impl;


import br.com.project.meetime.enums.ActionEnum;
import br.com.project.meetime.integration.AbstractIntegration;
import br.com.project.meetime.integration.HubSpotIntegration;
import br.com.project.meetime.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class HubSpotIntegrationImpl implements HubSpotIntegration {

    private final AbstractIntegration abstractIntegration;

    @Override
    public Mono<ResponseEntity<Void>> post(
            final ActionEnum actionEnum,
            final Object body,
            final String endpoint,
            final String token) {

        return abstractIntegration.post(body, endpoint,
                WebClientUtil.API_RETRY_ATTEMPTS,
                WebClientUtil.API_TIMEOUT,
                abstractIntegration.buildDefaultHeaders(token));
    }
}
