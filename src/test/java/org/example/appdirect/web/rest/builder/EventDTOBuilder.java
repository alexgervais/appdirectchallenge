package org.example.appdirect.web.rest.builder;

import org.example.appdirect.service.dto.*;

public class EventDTOBuilder {

    private String type;
    private String edition;
    private String creatorFirstName;
    private String creatorLastName;
    private String accountIdentifier;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userOpenId;

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

    public EventDTOBuilder withCreatorFirstName(final String creatorFirstName) {

        this.creatorFirstName = creatorFirstName;

        return this;
    }

    public EventDTOBuilder withCreatorLastName(final String creatorLastName) {

        this.creatorLastName = creatorLastName;

        return this;
    }

    public EventDTOBuilder withUserFirstName(final String userFirstName) {

        this.userFirstName = userFirstName;

        return this;
    }

    public EventDTOBuilder withUserLastName(final String userLastName) {

        this.userLastName = userLastName;

        return this;
    }

    public EventDTOBuilder withUserEmail(final String userEmail) {

        this.userEmail = userEmail;

        return this;
    }

    public EventDTOBuilder withUserOpenId(final String userOpenId) {

        this.userOpenId = userOpenId;

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

        final UserDTO user = new UserDTO();
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
        user.setEmail(userEmail);
        user.setOpenId(userOpenId);

        final PayloadDTO payload = new PayloadDTO();
        payload.setOrder(order);
        payload.setAccount(account);
        payload.setUser(user);

        final CreatorDTO creator = new CreatorDTO();
        creator.setFirstName(creatorFirstName);
        creator.setLastName(creatorLastName);

        final EventDTO event = new EventDTO();
        event.setType(type);
        event.setPayload(payload);
        event.setCreator(creator);

        return event;
    }
}
