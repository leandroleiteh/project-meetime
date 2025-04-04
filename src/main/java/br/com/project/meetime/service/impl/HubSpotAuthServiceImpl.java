package br.com.project.meetime.service.impl;

import br.com.project.meetime.mapper.AccessTokenHubspotMapper;
import br.com.project.meetime.service.HubSpotAuthService;
import br.com.project.rest.v1.model.AccessTokenHubspotResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static br.com.project.meetime.constant.GenericMessageConstant.Error.EXCHANGING_CODE;
import static br.com.project.meetime.constant.GenericMessageConstant.Info.SUCCESS_REQUEST;

@Log4j2
@RequiredArgsConstructor
@Service
public class HubSpotAuthServiceImpl implements HubSpotAuthService {

    private static final String GRANT_TYPE = "grant_type";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String REDIRECT_URI = "redirect_uri";
    private static final String CODE = "code";
    private static final String SCOPE = "scope";

    private final WebClient webClient;
    private final AccessTokenHubspotMapper accessTokenHubspotMapper;


    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.client-secret}")
    private String clientSecret;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    @Value("${hubspot.authorization-uri}")
    private String authorizationUri;

    @Value("${hubspot.token-uri}")
    private String tokenUri;

    @Value("${hubspot.authorization.code}")
    private String authorizationCode;

    @Value("${hubspot.scope}")
    private String scope;

    @Override
    public String getAuthorizationUrl() {

        log.info("URI Redirect: " + redirectUri);
        log.info("Defined Scope: " + scope);

        return UriComponentsBuilder.fromHttpUrl(authorizationUri)
                .queryParam(CLIENT_ID, clientId)
                .queryParam(REDIRECT_URI, redirectUri)
                .queryParam(SCOPE, scope)
                .toUriString();
    }

    @Override
    public CompletableFuture<AccessTokenHubspotResponse> exchangeAuthorizationCode(final String code) {

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(GRANT_TYPE, authorizationCode);
        requestBody.add(CLIENT_ID, clientId);
        requestBody.add(CLIENT_SECRET, clientSecret);
        requestBody.add(REDIRECT_URI, redirectUri);
        requestBody.add(CODE, code);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return webClient.post()
                .uri(tokenUri)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .map(accessTokenHubspotMapper::toResponse)
                .doOnSuccess(response -> log.info(SUCCESS_REQUEST, HttpStatus.OK))
                .doOnError(error -> log.error(EXCHANGING_CODE, error.getMessage())).toFuture();
    }
}

