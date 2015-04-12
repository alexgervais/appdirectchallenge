package org.example.appdirect.config;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

@Configuration
public class OAuthConsumerConfiguration {

    private final Logger log = LoggerFactory.getLogger(OAuthConsumerConfiguration.class);

    @Inject
    private Environment env;

    @Bean
    public OAuthConsumer oAuthConsumer() {

        final String key = env.getProperty("authentication.oauth.clientid", String.class, "Dummy");
        final String secret = env.getProperty("authentication.oauth.secret", String.class, "secret");

        log.debug(String.format("Using OAuth consumer key [%s]", key));

        return new DefaultOAuthConsumer(key, secret);
    }
}
