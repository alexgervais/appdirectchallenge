package org.example.appdirect.web.rest.builder;

import org.example.appdirect.service.dto.*;

public class EventDTOBuilder {

    private String type;
    private String edition;
    private String firstName;
    private String lastName;
    private String accountIdentifier;

    private EventDTOBuilder() {

    }

    public static EventDTOBuilder anEvent() {

        return new EventDTOBuilder();
    }

    public EventDTOBuilder withType(final String type) {

        this.type = type;

        return this;
    }

    public EventDTOBuilder withEdition(final String edition) {

        this.edition = edition;

        return this;
    }

    public EventDTOBuilder withFirstName(final String firstName) {

        this.firstName = firstName;

        return this;
    }

    public EventDTOBuilder withLastName(final String lastName) {

        this.lastName = lastName;

        return this;
    }

    public EventDTOBuilder withAccountIdentifier(final String accountIdentifier) {

        this.accountIdentifier = accountIdentifier;

        return this;
    }

    public EventDTO build() {

        final OrderDTO order = new OrderDTO();
        order.setEditionCode(edition);

        final AccountDTO account = new AccountDTO();
        account.setAccountIdentifier(accountIdentifier);

        final PayloadDTO payload = new PayloadDTO();
        payload.setOrder(order);
        payload.setAccount(account);

        final CreatorDTO creator = new CreatorDTO();
        creator.setFirstName(firstName);
        creator.setLastName(lastName);

        final EventDTO event = new EventDTO();
        event.setType(type);
        event.setPayload(payload);
        event.setCreator(creator);

        return event;
    }
}
