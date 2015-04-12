package org.example.appdirect.web.mapper;

import org.example.appdirect.domain.Authority;
import org.example.appdirect.domain.User;
import org.example.appdirect.web.rest.dto.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountMapper {

    public Account map(final User user) {

        final Account account = new Account();

        account.setLangKey(user.getLangKey());
        account.setEmail(user.getEmail());
        account.setFirstName(user.getFirstName());
        account.setLastName(user.getLastName());
        account.setLastName(user.getLastName());
        account.setLogin(user.getLogin());

        final List<String> roles = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            roles.add(authority.getName());
        }
        account.setRoles(roles);

        return account;
    }

}
