package org.example.appdirect.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security logout handler, specialized for Ajax requests.
 */
@Component
public class AjaxLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(AjaxLogoutSuccessHandler.class);

    @Inject
    private Environment env;

    @Override
    public void onLogoutSuccess(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Authentication authentication) throws IOException, ServletException {

        final String logoutUrl = env.getProperty("authentication.logout.url", String.class);
        final String openId = ((User) authentication.getPrincipal()).getUsername();

        final String fullLogoutUrl = logoutUrl + openId;

        log.debug(String.format("Redirecting to [%s]", fullLogoutUrl));

        response.sendRedirect(fullLogoutUrl);
    }
}
