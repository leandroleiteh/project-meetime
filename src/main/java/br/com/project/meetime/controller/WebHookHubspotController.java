package br.com.project.meetime.controller;

import br.com.project.meetime.constant.ObservabilityTagConstant;
import br.com.project.meetime.enums.ActionEnum;
import br.com.project.meetime.enums.HubspotEndpointEnum;
import br.com.project.meetime.enums.StatusRequestEnum;
import br.com.project.meetime.util.LogUtil;
import br.com.project.rest.v1.api.WebhooksApi;
import br.com.project.rest.v1.model.WebhookHubspotCreateContactRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Log4j2
public class WebHookHubspotController extends BaseController implements WebhooksApi {

//    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
//    @PostMapping("/webhook/contact-creation")
//    @Override
//    public ResponseEntity<Void> handleWebhook(@RequestBody final WebhookHubspotCreateContactRequest contactRequest) {
//
//        long initExecutionTime = System.currentTimeMillis();
//        log.info(LogUtil.buildMessage("<< [INIT REQUEST][WEBHOOK HUBSPOT] PROCESSING >>")
//                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.WEBHOOK_CONTACT_CREATION.getPath())
//                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.PROCESSING));
//
//        printWebhookContacts(contactRequest);
//
//        log.info(LogUtil.buildMessage("<< [END REQUEST][WEBHOOK HUBSPOT] PROCESSING, SUCCESSFUL >>")
//                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.WEBHOOK_CONTACT_CREATION.getPath())
//                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.PROCESSING)
//                .with(ObservabilityTagConstant.Info.STATUS_REQUEST, StatusRequestEnum.SUCCESS)
//                .with(ObservabilityTagConstant.Info.RESPONSE_TIME,
//                        (System.currentTimeMillis() - initExecutionTime)));
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping("/webhook/contact-creation")
    public ResponseEntity<Void> handleWebhook(@RequestBody final String contactRequest) {

        long initExecutionTime = System.currentTimeMillis();
        log.info(LogUtil.buildMessage("<< [INIT REQUEST][WEBHOOK HUBSPOT] PROCESSING >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.WEBHOOK_CONTACT_CREATION.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.PROCESSING));

        log.info(contactRequest.toString());

        log.info(LogUtil.buildMessage("<< [END REQUEST][WEBHOOK HUBSPOT] PROCESSING, SUCCESSFUL >>")
                .with(ObservabilityTagConstant.Info.PAGE, HubspotEndpointEnum.WEBHOOK_CONTACT_CREATION.getPath())
                .with(ObservabilityTagConstant.Info.ACTION, ActionEnum.PROCESSING)
                .with(ObservabilityTagConstant.Info.STATUS_REQUEST, StatusRequestEnum.SUCCESS)
                .with(ObservabilityTagConstant.Info.RESPONSE_TIME,
                        (System.currentTimeMillis() - initExecutionTime)));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void printWebhookContacts(WebhookHubspotCreateContactRequest contactRequest) {

        contactRequest.getResults().forEach(contact -> {

            System.out.println("-------------------------------");
            System.out.println("Object ID: " + contact.getObjectId());
            System.out.println("Change Source: " + contact.getChangeSource());
            System.out.println("Event ID: " + contact.getEventId());
            System.out.println("Subscription ID: " + contact.getSubscriptionId());
            System.out.println("Portal ID: " + contact.getPortalId());
            System.out.println("App ID: " + contact.getAppId());
            System.out.println("Occurred At: " + contact.getOccurredAt());
            System.out.println("Attempt Number: " + contact.getAttemptNumber());
            System.out.println("-------------------------------");
        });
    }
}
