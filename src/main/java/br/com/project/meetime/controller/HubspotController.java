package br.com.project.meetime.controller;

import br.com.project.meetime.constant.ObservabilityTagConstant;
import br.com.project.meetime.enums.ActionEnum;
import br.com.project.meetime.enums.HubspotEndpointEnum;
import br.com.project.meetime.enums.StatusRequestEnum;
import br.com.project.meetime.service.HubspotService;
import br.com.project.meetime.util.LogUtil;
import br.com.project.rest.v1.api.ContactsApi;
import br.com.project.rest.v1.model.ContactRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class HubspotController extends BaseController implements ContactsApi {

    private final HubspotService hubspotService;

    @PostMapping("/contact")
    @Override
    public ResponseEntity<Void> createContact(
            @RequestHeader("token") final String token,
            @RequestBody final ContactRequest contactRequest) {

        long initExecutionTime = System.currentTimeMillis();
        log.info(LogUtil.buildMessage("<< [INIT REQUEST][CREATE CONTACTION] PROCESSING >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.CREATE_CONTACT.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.SAVE));

        hubspotService.createContact(token, contactRequest);

        log.info(LogUtil.buildMessage("<< [END REQUEST][CREATE CONTACTION] PROCESSING, SUCCESSFUL >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.CREATE_CONTACT.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.SAVE)
                .with(ObservabilityTagConstant.Info.STATUS_REQUEST, StatusRequestEnum.SUCCESS)
                .with(ObservabilityTagConstant.Info.RESPONSE_TIME,
                        (System.currentTimeMillis() - initExecutionTime)));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
