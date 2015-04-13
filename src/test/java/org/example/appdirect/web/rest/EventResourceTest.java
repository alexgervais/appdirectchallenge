package org.example.appdirect.web.rest;

import org.example.appdirect.Application;
import org.example.appdirect.domain.Subscription;
import org.example.appdirect.repository.SubscriptionRepository;
import org.example.appdirect.service.AppDirectAPIException;
import org.example.appdirect.service.AppDirectEventAPIClient;
import org.example.appdirect.service.dto.EventDTO;
import org.example.appdirect.web.mapper.SubscriptionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.example.appdirect.web.rest.builder.EventDTOBuilder.anEvent;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventResourceTest {

    @Inject
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private AppDirectEventAPIClient appDirectEventAPIClient;

    private MockMvc restEventMockMvc;

    @PostConstruct
    public void setup() {

        MockitoAnnotations.initMocks(this);

        final EventResource eventResource = new EventResource();
        ReflectionTestUtils.setField(eventResource, "subscriptionRepository", subscriptionRepository);
        ReflectionTestUtils.setField(eventResource, "subscriptionMapper", subscriptionMapper);
        ReflectionTestUtils.setField(eventResource, "appDirectEventAPIClient", appDirectEventAPIClient);

        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource).build();
    }

    @Test
    @Transactional
    public void createSubscriptionEvent() throws Exception {

        final EventDTO event = anEvent().withType("CREATION_EVENT")
            .withEdition("LIMITED")
            .withFirstName("Alex")
            .withLastName("Gervais")
            .build();

        when(appDirectEventAPIClient.getSubscriptionEvent("http://www.google.ca")).thenReturn(event);

        final ResultActions request = restEventMockMvc.perform(get("/api/events/create?url=http://www.google.ca"));

        final ArgumentCaptor<Subscription> subscriptionArgumentCaptor = ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionRepository).save(subscriptionArgumentCaptor.capture());


        final Subscription subscription = subscriptionArgumentCaptor.getValue();

        assertThat(subscription.getEdition(), is(equalTo("LIMITED")));
        assertThat(subscription.getName(), is(equalTo("Alex Gervais")));
        assertThat(subscription.getEventType(), is(equalTo("CREATION_EVENT")));

        request
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<result xmlns=\"\">" +
                "<success>true</success>" +
                "<accountIdentifier>" + subscription.getAccountIdentifier() + "</accountIdentifier>" +
                "</result>")));
    }

    @Test
    @Transactional
    public void createSubscriptionEvent_errorHandling() throws Exception {

        when(appDirectEventAPIClient.getSubscriptionEvent("http://www.google.ca")).thenThrow(new AppDirectAPIException("url", null));

        restEventMockMvc.perform(get("/api/events/create?url=http://www.google.ca"))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(content().string("<result xmlns=\"\">" +
                "<success>false</success>" +
                "<errorCode>UNKNOWN_ERROR</errorCode>" +
                "<message>An error occurred while requesting event url [url]</message>" +
                "</result>"));
    }


    @Test
    @Transactional
    public void updateSubscriptionEvent() throws Exception {

        final EventDTO event = anEvent().withType("UPDATE_EVENT")
            .withFirstName("Alex")
            .withAccountIdentifier("ABCD")
            .build();

        when(appDirectEventAPIClient.getSubscriptionEvent("http://www.google.ca")).thenReturn(event);

        final ResultActions request = restEventMockMvc.perform(get("/api/events/update?url=http://www.google.ca"));

        final ArgumentCaptor<Subscription> subscriptionArgumentCaptor = ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionRepository).save(subscriptionArgumentCaptor.capture());

        final Subscription subscription = subscriptionArgumentCaptor.getValue();

        assertThat(subscription.getEdition(), is(nullValue()));
        assertThat(subscription.getName(), is(equalTo("Alex")));
        assertThat(subscription.getEventType(), is(equalTo("UPDATE_EVENT")));
        assertThat(subscription.getAccountIdentifier(), is(equalTo("ABCD")));

        request
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<result xmlns=\"\">" +
                "<success>true</success>" +
                "</result>")));
    }

    @Test
    @Transactional
    public void updateSubscriptionEvent_errorHandling() throws Exception {

        when(appDirectEventAPIClient.getSubscriptionEvent("http://www.google.ca")).thenThrow(new AppDirectAPIException("url", null));

        restEventMockMvc.perform(get("/api/events/update?url=http://www.google.ca"))
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_XML))
            .andExpect(content().string("<result xmlns=\"\">" +
                "<success>false</success>" +
                "<errorCode>UNKNOWN_ERROR</errorCode>" +
                "<message>An error occurred while requesting event url [url]</message>" +
                "</result>"));
    }

}
