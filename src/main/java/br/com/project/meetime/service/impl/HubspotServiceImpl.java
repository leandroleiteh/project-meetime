package br.com.project.meetime.service.impl;

import br.com.project.meetime.constant.GenericMessageConstant;
import br.com.project.meetime.enums.ActionEnum;
import br.com.project.meetime.enums.HubspotEndpointEnum;
import br.com.project.meetime.exception.ApiException;
import br.com.project.meetime.exception.ServiceException;
import br.com.project.meetime.integration.HubSpotIntegration;
import br.com.project.meetime.service.HubspotService;
import br.com.project.rest.v1.model.ContactRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class HubspotServiceImpl implements HubspotService {

    private final HubSpotIntegration hubspotIntegration;

    @Value("${hubspot.base.endpoint}")
    private String baseEndpoint;

    @Override
    public void createContact(
            final String token,
            final ContactRequest contactRequest) {

        try {

            if (Objects.nonNull(contactRequest) && Objects.nonNull(token)) {

                String endpoint = UriComponentsBuilder.fromUriString(
                                baseEndpoint)
                        .path(HubspotEndpointEnum.CREATE_CONTACT.getPath())
                        .toUriString();

                hubspotIntegration.post(
                        ActionEnum.SAVE,
                        contactRequest,
                        endpoint,
                        token).toFuture().join();
            }

        } catch (ApiException e) {

            throw new ServiceException(
                    HubspotEndpointEnum.CREATE_CONTACT.getPath(),
                    ActionEnum.SAVE,
                    GenericMessageConstant.Error.NOT_SAVED,
                    e.getHttpStatusCode(),
                    e);
        }
    }
}
