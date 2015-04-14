package org.example.appdirect.web.mapper;

import org.example.appdirect.web.rest.dto.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountMapper {

    public Account map(final UserDetails userDetails) {

        final Account account = new Account();

        account.setLogin(userDetails.getUsername());

        final List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        account.setRoles(roles);

        return account;
    }

}
