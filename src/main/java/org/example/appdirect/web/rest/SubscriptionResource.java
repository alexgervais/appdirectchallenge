package org.example.appdirect.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.example.appdirect.domain.Subscription;
import org.example.appdirect.repository.SubscriptionRepository;
import org.example.appdirect.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionResource.class);

    @Inject
    private SubscriptionRepository subscriptionRepository;

    /**
     * GET  /subscriptions -> get all the subscriptions.
     */
    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Subscription>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                                     @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

        Page<Subscription> page = subscriptionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subscriptions", offset, limit);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subscriptions/:id -> get the "id" subscription.
     */
    @RequestMapping(value = "/subscriptions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subscription> get(@PathVariable Long id) {

        log.debug("REST request to get Subscription : {}", id);
        Subscription subscription = subscriptionRepository.findOne(id);
        if (subscription == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }
}
