package org.example.appdirect.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService, AuthenticationUserDetailsService {

    @Inject
    private SecurityUtils securityUtils;

    @Override
    public UserDetails loadUserByUsername(final String openId) throws UsernameNotFoundException, DataAccessException {

        final GrantedAuthority userRole = new SimpleGrantedAuthority(AuthoritiesConstants.USER);

        return new User(openId, "", Collections.singletonList(userRole));
    }

    @Override
    public UserDetails loadUserDetails(final Authentication authentication) throws UsernameNotFoundException {

        return loadUserByUsername((String) authentication.getPrincipal());
    }

    public UserDetails getCurrentUserDetails() {

        final Authentication authentication = securityUtils.getCurrentAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                return (UserDetails) authentication.getPrincipal();
            }
        }

        return null;
    }
}
