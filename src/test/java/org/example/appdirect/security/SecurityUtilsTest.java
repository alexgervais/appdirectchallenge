package org.example.appdirect.security;

import org.example.appdirect.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the SecurityUtils utility class.
 *
 * @see SecurityUtils
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SecurityUtilsTest {

    @Inject
    private SecurityUtils securityUtils;

    @Test
    public void getCurrentLogin() {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "pass"));
        SecurityContextHolder.setContext(securityContext);

        Authentication authentication = securityUtils.getCurrentAuthentication();

        assertThat((String) authentication.getPrincipal()).isEqualTo("admin");
        assertThat((String) authentication.getCredentials()).isEqualTo("pass");
    }

    @Test
    public void isAuthenticated() {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);

        boolean isAuthenticated = securityUtils.isAuthenticated();

        assertThat(isAuthenticated).isTrue();
    }

    @Test
    public void anonymousIsNotAuthenticated() {

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities));
        SecurityContextHolder.setContext(securityContext);

        boolean isAuthenticated = securityUtils.isAuthenticated();

        assertThat(isAuthenticated).isFalse();
    }
}
