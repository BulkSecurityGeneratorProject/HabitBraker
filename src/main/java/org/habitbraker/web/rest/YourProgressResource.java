package org.habitbraker.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.habitbraker.domain.YourProgress;
import org.habitbraker.service.YourProgressService;
import org.habitbraker.web.rest.util.HeaderUtil;
import org.habitbraker.web.rest.util.PaginationUtil;
import org.habitbraker.web.rest.dto.YourProgressDTO;
import org.habitbraker.web.rest.mapper.YourProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing YourProgress.
 */
@RestController
@RequestMapping("/api")
public class YourProgressResource {

    private final Logger log = LoggerFactory.getLogger(YourProgressResource.class);
        
    @Inject
    private YourProgressService yourProgressService;
    
    @Inject
    private YourProgressMapper yourProgressMapper;
    
    /**
     * POST  /your-progresses : Create a new yourProgress.
     *
     * @param yourProgressDTO the yourProgressDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new yourProgressDTO, or with status 400 (Bad Request) if the yourProgress has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/your-progresses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YourProgressDTO> createYourProgress(@Valid @RequestBody YourProgressDTO yourProgressDTO) throws URISyntaxException {
        log.debug("REST request to save YourProgress : {}", yourProgressDTO);
        if (yourProgressDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yourProgress", "idexists", "A new yourProgress cannot already have an ID")).body(null);
        }
        YourProgressDTO result = yourProgressService.save(yourProgressDTO);
        return ResponseEntity.created(new URI("/api/your-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yourProgress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /your-progresses : Updates an existing yourProgress.
     *
     * @param yourProgressDTO the yourProgressDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated yourProgressDTO,
     * or with status 400 (Bad Request) if the yourProgressDTO is not valid,
     * or with status 500 (Internal Server Error) if the yourProgressDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/your-progresses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YourProgressDTO> updateYourProgress(@Valid @RequestBody YourProgressDTO yourProgressDTO) throws URISyntaxException {
        log.debug("REST request to update YourProgress : {}", yourProgressDTO);
        if (yourProgressDTO.getId() == null) {
            return createYourProgress(yourProgressDTO);
        }
        YourProgressDTO result = yourProgressService.save(yourProgressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yourProgress", yourProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /your-progresses : get all the yourProgresses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of yourProgresses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/your-progresses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<YourProgressDTO>> getAllYourProgresses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of YourProgresses");
        Page<YourProgress> page = yourProgressService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/your-progresses");
        return new ResponseEntity<>(yourProgressMapper.yourProgressesToYourProgressDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /your-progresses/:id : get the "id" yourProgress.
     *
     * @param id the id of the yourProgressDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the yourProgressDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/your-progresses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YourProgressDTO> getYourProgress(@PathVariable Long id) {
        log.debug("REST request to get YourProgress : {}", id);
        YourProgressDTO yourProgressDTO = yourProgressService.findOne(id);
        return Optional.ofNullable(yourProgressDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /your-progresses/:id : delete the "id" yourProgress.
     *
     * @param id the id of the yourProgressDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/your-progresses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYourProgress(@PathVariable Long id) {
        log.debug("REST request to delete YourProgress : {}", id);
        yourProgressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yourProgress", id.toString())).build();
    }

}
