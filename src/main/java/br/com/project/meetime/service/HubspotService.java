package br.com.project.meetime.service;

import br.com.project.rest.v1.model.ContactRequest;

public interface HubspotService {

    void createContact(final String token, final ContactRequest contactRequest);
}
