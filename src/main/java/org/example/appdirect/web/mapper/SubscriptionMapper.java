package org.example.appdirect.web.mapper;

import org.example.appdirect.domain.Subscription;
import org.example.appdirect.service.dto.EventDTO;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionMapper {

    public Subscription map(final EventDTO eventDTO) {

        final Subscription subscription = new Subscription();

        subscription.setEventType(eventDTO.getType());

        if (eventDTO.getPayload().getAccount() != null) {
            subscription.setAccountIdentifier(eventDTO.getPayload().getAccount().getAccountIdentifier());
        }

        if (eventDTO.getPayload().getOrder() != null) {
            subscription.setEdition(eventDTO.getPayload().getOrder().getEditionCode());
        }

        if (eventDTO.getCreator() != null) {
            subscription.setName(String.format("%s %s", eventDTO.getCreator().getFirstName(), eventDTO.getCreator().getLastName()));
        }

        return subscription;
    }

}
