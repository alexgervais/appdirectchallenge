package org.example.appdirect.web.rest;

import org.example.appdirect.Application;
import org.example.appdirect.domain.Access;
import org.example.appdirect.repository.AccessRepository;
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
 * Test class for the AccessResource REST controller.
 *
 * @see AccessResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AccessResourceTest {

    private static final String DEFAULT_EVENT_TYPE = "SAMPLE_EVENT_TYPE";
    private static final String DEFAULT_NAME = "SAMPLE_NAME";
    private static final String DEFAULT_ACCOUNT_IDENTIFIER = "SAMPLE_ACCOUNT_IDENTIFIER";
    private static final String DEFAULT_OPEN_ID = "SAMPLE_OPEN_ID";
    private static final String DEFAULT_EMAIL = "SAMPLE_EMAIL";

    @Inject
    private AccessRepository accessRepository;

    private MockMvc restAccessMockMvc;

    private Access access;

    @PostConstruct
    public void setup() {

        MockitoAnnotations.initMocks(this);

        final AccessResource accessResource = new AccessResource();
        ReflectionTestUtils.setField(accessResource, "accessRepository", accessRepository);

        this.restAccessMockMvc = MockMvcBuilders.standaloneSetup(accessResource).build();
    }

    @Before
    public void initTest() {

        access = new Access();
        access.setEventType(DEFAULT_EVENT_TYPE);
        access.setAccountIdentifier(DEFAULT_ACCOUNT_IDENTIFIER);
        access.setName(DEFAULT_NAME);
        access.setEmail(DEFAULT_EMAIL);
        access.setOpenId(DEFAULT_OPEN_ID);
    }

    @Test
    @Transactional
    public void getAllAccesses() throws Exception {

        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accesses
        restAccessMockMvc.perform(get("/api/accesses"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(access.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].accountIdentifier").value(hasItem(DEFAULT_ACCOUNT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].openId").value(hasItem(DEFAULT_OPEN_ID)));
    }
}
