package org.example.appdirect.web.mapper;

import org.example.appdirect.domain.Access;
import org.example.appdirect.service.dto.EventDTO;
import org.example.appdirect.service.dto.UserDTO;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class AccessMapper {

    public Access map(final EventDTO eventDTO) {

        final Access access = new Access();

        access.setEventType(eventDTO.getType());

        if (eventDTO.getPayload().getAccount() != null) {
            access.setAccountIdentifier(eventDTO.getPayload().getAccount().getAccountIdentifier());
        }

        final UserDTO user = eventDTO.getPayload().getUser();
        if (user != null) {
            final StringBuilder stringBuilder = new StringBuilder();
            if (!isEmpty(user.getFirstName())) {
                stringBuilder.append(user.getFirstName()).append(" ");
            }
            if (!isEmpty(user.getLastName())) {
                stringBuilder.append(user.getLastName());
            }

            access.setName(stringBuilder.toString().trim());
            access.setOpenId(user.getOpenId());
            access.setEmail(user.getEmail());
        }

        return access;
    }

}
