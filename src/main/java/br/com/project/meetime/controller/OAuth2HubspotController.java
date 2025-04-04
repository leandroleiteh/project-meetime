package br.com.project.meetime.controller;

import br.com.project.meetime.constant.ObservabilityTagConstant;
import br.com.project.meetime.enums.ActionEnum;
import br.com.project.meetime.enums.HubspotEndpointEnum;
import br.com.project.meetime.enums.StatusRequestEnum;
import br.com.project.meetime.service.HubSpotAuthService;
import br.com.project.meetime.util.LogUtil;
import br.com.project.rest.v1.api.OAuthApi;
import br.com.project.rest.v1.model.AccessTokenHubspotResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class OAuth2HubspotController extends BaseController implements OAuthApi {

    private final HubSpotAuthService authService;

    @Override
    @GetMapping("/oauth2/authorize")
    public ResponseEntity<String> generateAuthorizationUrl() {

        long initExecutionTime = System.currentTimeMillis();
        log.info(LogUtil.buildMessage("<< [INIT REQUEST][AUTHORIZE URL] PROCESSING >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.OUATH2_AUTHORIZE.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.GET));

        var response = authService.getAuthorizationUrl();

        log.info(LogUtil.buildMessage("<< [END REQUEST][AUTHORIZE URL] PROCESSING, SUCCESSFUL >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.OUATH2_AUTHORIZE.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.GET)
                .with(ObservabilityTagConstant.Info.STATUS_REQUEST, StatusRequestEnum.SUCCESS)
                .with(ObservabilityTagConstant.Info.RESPONSE_TIME,
                        (System.currentTimeMillis() - initExecutionTime)));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/oauth2/callback")
    public ResponseEntity<AccessTokenHubspotResponse> handleOAuthCallback(@RequestParam("code") final String code) {

        long initExecutionTime = System.currentTimeMillis();
        log.info(LogUtil.buildMessage("<< [INIT REQUEST][CALLBACK URL] PROCESSING >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.OUATH2_CALLBACK.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.GET));

        var response = authService.exchangeAuthorizationCode(code);

        log.info(LogUtil.buildMessage("<< [END REQUEST][CALLBACK URL] PROCESSING, SUCCESSFUL >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.OUATH2_CALLBACK.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.GET)
                .with(ObservabilityTagConstant.Info.STATUS_REQUEST, StatusRequestEnum.SUCCESS)
                .with(ObservabilityTagConstant.Info.RESPONSE_TIME,
                        (System.currentTimeMillis() - initExecutionTime)));

        return ResponseEntity.status(HttpStatus.CREATED).body(response.join());
    }
}
