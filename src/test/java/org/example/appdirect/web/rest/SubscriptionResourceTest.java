package org.example.appdirect.web.rest;

import org.example.appdirect.Application;
import org.example.appdirect.domain.Subscription;
import org.example.appdirect.repository.SubscriptionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SubscriptionResource REST controller.
 *
 * @see SubscriptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubscriptionResourceTest {

    private static final String DEFAULT_EDITION = "SAMPLE_EDITION";
    private static final String DEFAULT_EVENT_TYPE = "SAMPLE_EVENT_TYPE";
    private static final String DEFAULT_NAME = "SAMPLE_NAME";
    private static final String DEFAULT_ACCOUNT_IDENTIFIER = "SAMPLE_ACCOUNT_IDENTIFIER";

    @Inject
    private SubscriptionRepository subscriptionRepository;

    private MockMvc restSubscriptionMockMvc;

    private Subscription subscription;

    @PostConstruct
    public void setup() {

        MockitoAnnotations.initMocks(this);

        final SubscriptionResource subscriptionResource = new SubscriptionResource();
        ReflectionTestUtils.setField(subscriptionResource, "subscriptionRepository", subscriptionRepository);

        this.restSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(subscriptionResource).build();
    }

    @Before
    public void initTest() {

        subscription = new Subscription();
        subscription.setEdition(DEFAULT_EDITION);
        subscription.setEventType(DEFAULT_EVENT_TYPE);
        subscription.setName(DEFAULT_NAME);
        subscription.setAccountIdentifier(DEFAULT_ACCOUNT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllSubscriptions() throws Exception {

        // Initialize the database
        subscriptionRepository.saveAndFlush(subscription);

        // Get all the subscriptions
        restSubscriptionMockMvc.perform(get("/api/subscriptions"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].accountIdentifier").value(hasItem(DEFAULT_ACCOUNT_IDENTIFIER)));
    }

    @Test
    @Transactional
    public void getSubscription() throws Exception {

        // Initialize the database
        subscriptionRepository.saveAndFlush(subscription);

        // Get the subscription
        restSubscriptionMockMvc.perform(get("/api/subscriptions/{id}", subscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subscription.getId().intValue()))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.accountIdentifier").value(DEFAULT_ACCOUNT_IDENTIFIER));
    }

    @Test
    @Transactional
    public void getNonExistingSubscription() throws Exception {

        // Get the subscription
        restSubscriptionMockMvc.perform(get("/api/subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
