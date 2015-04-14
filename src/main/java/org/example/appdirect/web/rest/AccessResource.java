package org.example.appdirect.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.example.appdirect.domain.Access;
import org.example.appdirect.repository.AccessRepository;
import org.example.appdirect.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccessResource {

    @Inject
    private AccessRepository accessRepository;

    /**
     * GET  /accesses -> get all the accesses.
     */
    @RequestMapping(value = "/accesses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Access>> getAll(@RequestParam(value = "page", required = false) Integer offset,
                                               @RequestParam(value = "per_page", required = false) Integer limit) throws URISyntaxException {

        final Page<Access> page = accessRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        final HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accesses", offset, limit);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
