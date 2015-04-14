package org.example.appdirect.config;

import com.google.common.collect.ImmutableMap;
import org.example.appdirect.security.AjaxLogoutSuccessHandler;
import org.example.appdirect.security.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;
import org.springframework.security.oauth.provider.InMemoryConsumerDetailsService;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.openid.OpenIDAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private Environment env;

    @Override
    public void configure(final WebSecurity web) throws Exception {

        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler);

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated()
            .and()
            .openidLogin()
            .authenticationUserDetailsService(userDetailsService)
            .loginProcessingUrl("/api/login")
            .permitAll()
            .defaultSuccessUrl("/");

        http.addFilterBefore(oAuthProviderProcessingFilter(), OpenIDAuthenticationFilter.class);
    }

    @Bean
    public OAuthProviderProcessingFilter oAuthProviderProcessingFilter() {

        final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter() {

            final AntPathRequestMatcher antPath = new AntPathRequestMatcher("/api/events/**");

            @Override
            protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {

                final boolean matches = antPath.matches(request);
                if (matches) {
                    final OAuthProcessingFilterEntryPoint authenticationEntryPoint = new OAuthProcessingFilterEntryPoint();
                    setAuthenticationEntryPoint(authenticationEntryPoint);
                    final String realmName = request.getRequestURL().toString();
                    authenticationEntryPoint.setRealmName(realmName);
                }

                return matches;
            }
        };
        filter.setConsumerDetailsService(consumerDetailsService());
        filter.setTokenServices(inMemoryProviderTokenServices());

        return filter;
    }

    @Bean
    public ConsumerDetailsService consumerDetailsService() {

        final String key = env.getProperty("authentication.oauth.clientid", String.class, "Dummy");
        final String secret = env.getProperty("authentication.oauth.secret", String.class, "secret");

        final BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
        consumerDetails.setConsumerKey(key);
        consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(secret));
        consumerDetails.setRequiredToObtainAuthenticatedToken(false);

        final InMemoryConsumerDetailsService consumerDetailsService = new InMemoryConsumerDetailsService();
        consumerDetailsService.setConsumerDetailsStore(ImmutableMap.of(key, consumerDetails));

        return consumerDetailsService;
    }

    @Bean
    public InMemoryProviderTokenServices inMemoryProviderTokenServices() {

        return new InMemoryProviderTokenServices();
    }
}
