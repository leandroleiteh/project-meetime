package br.com.project.meetime.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HubspotEndpointEnum {

    CREATE_CONTACT("/objects/contacts", "endpoint para criar contatos"),
    WEBHOOK_CONTACT_CREATION("/webhook/contact-creation", "endpoint recebimento webhook"),
    OUATH2_AUTHORIZE("/oauth2/authorize", "endpoint para recebimento da url de autorização"),
    OUATH2_CALLBACK("/oauth2/callback", "endpoint para recebimento da url de callback");

    public String path;
    public String about;
}
