package org.example.appdirect.web.mapper;

import org.example.appdirect.domain.Subscription;
import org.example.appdirect.service.dto.CreatorDTO;
import org.example.appdirect.service.dto.EventDTO;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

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

        final CreatorDTO creator = eventDTO.getCreator();
        if (creator != null) {
            final StringBuilder stringBuilder = new StringBuilder();
            if (!isEmpty(creator.getFirstName())) {
                stringBuilder.append(creator.getFirstName()).append(" ");
            }
            if (!isEmpty(creator.getLastName())) {
                stringBuilder.append(creator.getLastName());
            }
            subscription.setName(stringBuilder.toString().trim());
        }

        return subscription;
    }

}
