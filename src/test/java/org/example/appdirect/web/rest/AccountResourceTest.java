package org.example.appdirect.web.rest;

import org.example.appdirect.Application;
import org.example.appdirect.security.AuthoritiesConstants;
import org.example.appdirect.security.UserDetailsService;
import org.example.appdirect.web.mapper.AccountMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountResource REST controller.
 *
 * @see UserDetailsService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AccountResourceTest {

    @Inject
    private AccountMapper accountMapper;

    @Mock
    private UserDetailsService mockUserDetailsService;

    private MockMvc restMvc;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        AccountResource accountResource = new AccountResource();
        ReflectionTestUtils.setField(accountResource, "userDetailsService", mockUserDetailsService);
        ReflectionTestUtils.setField(accountResource, "accountMapper", accountMapper);

        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource).build();
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {

        restMvc.perform(get("/api/authenticate")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    public void testAuthenticatedUser() throws Exception {

        restMvc.perform(get("/api/authenticate")
            .with(new RequestPostProcessor() {
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {

                    request.setRemoteUser("test");
                    return request;
                }
            })
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {

        final User user = new User("test", "", Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS)));

        when(mockUserDetailsService.getCurrentUserDetails()).thenReturn(user);

        restMvc.perform(get("/api/account")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.login").value("test"))
            .andExpect(jsonPath("$.roles").value(AuthoritiesConstants.ANONYMOUS));
    }

    @Test
    public void testGetUnknownAccount() throws Exception {

        when(mockUserDetailsService.getCurrentUserDetails()).thenReturn(null);

        restMvc.perform(get("/api/account")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }
}
