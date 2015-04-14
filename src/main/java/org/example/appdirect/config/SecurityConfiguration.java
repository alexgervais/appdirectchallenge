package org.example.appdirect.config;

import com.google.common.collect.ImmutableMap;
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
import org.springframework.security.oauth.provider.filter.OAuthProviderProcessingFilter;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.openid.OpenIDAuthenticationFilter;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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
            .antMatchers("/assets/**")
            .antMatchers("/login");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
            .filterSecurityInterceptorOncePerRequest(false)
            .antMatchers("/api/events/**").authenticated()
            .anyRequest().permitAll()
            ////
            .and()
            .openidLogin()
            .authenticationUserDetailsService(userDetailsService)
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/");

        http.addFilterBefore(oAuthProviderProcessingFilter(), OpenIDAuthenticationFilter.class);
    }

    @Bean
    public OAuthProviderProcessingFilter oAuthProviderProcessingFilter() {

        final ProtectedResourceProcessingFilter filter = new ProtectedResourceProcessingFilter();
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
