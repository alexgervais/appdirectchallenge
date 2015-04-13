package org.example.appdirect.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.example.appdirect.domain.Subscription;
import org.example.appdirect.repository.SubscriptionRepository;
import org.example.appdirect.service.AppDirectEventAPIClient;
import org.example.appdirect.service.dto.EventDTO;
import org.example.appdirect.web.mapper.SubscriptionMapper;
import org.example.appdirect.web.rest.dto.CreationResponse;
import org.example.appdirect.web.rest.dto.ErrorResponse;
import org.example.appdirect.web.rest.dto.Response;
import org.example.appdirect.web.rest.dto.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventResource {

    private final Logger log = LoggerFactory.getLogger(EventResource.class);

    @Inject
    private SubscriptionRepository subscriptionRepository;

    @Inject
    private SubscriptionMapper subscriptionMapper;

    @Inject
    private AppDirectEventAPIClient appDirectEventAPIClient;

    /**
     * GET  /create?url={eventUrl} -> handle the subscription order event located at "url".
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    @Timed
    public ResponseEntity<Response> create(@RequestParam("url") String eventUrl) {

        log.debug(String.format("REST request to create Event : [%s]", eventUrl));

        try {
            final EventDTO event = appDirectEventAPIClient.getSubscriptionEvent(eventUrl);
            final Subscription subscription = subscriptionMapper.map(event);
            subscription.setAccountIdentifier(UUID.randomUUID().toString());
            subscriptionRepository.save(subscription);

            return creationResponse(subscription);

        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    /**
     * GET  /update?url={eventUrl} -> handle the subscription change and cancel event located at "url".
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    @Timed
    public ResponseEntity<Response> update(@RequestParam("url") String eventUrl) {

        log.debug(String.format("REST request to update Event : [%s]", eventUrl));

        try {
            final EventDTO event = appDirectEventAPIClient.getSubscriptionEvent(eventUrl);
            final Subscription subscription = subscriptionMapper.map(event);
            subscriptionRepository.save(subscription);

            return updateResponse();

        } catch (Exception e) {
            return errorResponse(e);
        }
    }

    private ResponseEntity<Response> creationResponse(final Subscription subscription) {

        final CreationResponse creationResponse = new CreationResponse();
        creationResponse.setAccountIdentifier(subscription.getAccountIdentifier());

        return new ResponseEntity<Response>(creationResponse, HttpStatus.OK);
    }

    private ResponseEntity<Response> updateResponse() {

        final UpdateResponse updateResponse = new UpdateResponse();

        return new ResponseEntity<Response>(updateResponse, HttpStatus.OK);
    }

    private ResponseEntity<Response> errorResponse(final Exception e) {

        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());

        return new ResponseEntity<Response>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
