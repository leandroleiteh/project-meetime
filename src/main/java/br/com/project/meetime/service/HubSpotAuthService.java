package br.com.project.meetime.service;

import br.com.project.rest.v1.model.AccessTokenHubspotResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface HubSpotAuthService {

    String getAuthorizationUrl();

    CompletableFuture<AccessTokenHubspotResponse> exchangeAuthorizationCode(final String code);
}
