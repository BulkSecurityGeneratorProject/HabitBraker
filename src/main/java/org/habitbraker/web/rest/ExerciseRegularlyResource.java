package org.habitbraker.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.habitbraker.domain.ExerciseRegularly;
import org.habitbraker.domain.User;
import org.habitbraker.repository.UserRepository;
import org.habitbraker.security.AuthoritiesConstants;
import org.habitbraker.security.SecurityUtils;
import org.habitbraker.service.ExerciseRegularlyService;
import org.habitbraker.web.rest.util.HeaderUtil;
import org.habitbraker.web.rest.util.PaginationUtil;
import org.habitbraker.web.rest.dto.ExerciseRegularlyDTO;
import org.habitbraker.web.rest.mapper.ExerciseRegularlyMapper;
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
 * REST controller for managing ExerciseRegularly.
 */
@RestController
@RequestMapping("/api")
public class ExerciseRegularlyResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseRegularlyResource.class);
        
    @Inject
    private ExerciseRegularlyService exerciseRegularlyService;
    
    @Inject
    private ExerciseRegularlyMapper exerciseRegularlyMapper;
    
    @Inject
    private UserRepository userRepository;
    
    /**
     * POST  /exercise-regularlies : Create a new exerciseRegularly.
     *
     * @param exerciseRegularlyDTO the exerciseRegularlyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exerciseRegularlyDTO, or with status 400 (Bad Request) if the exerciseRegularly has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exercise-regularlies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseRegularlyDTO> createExerciseRegularly(@Valid @RequestBody ExerciseRegularlyDTO exerciseRegularlyDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseRegularly : {}", exerciseRegularlyDTO);
        if (exerciseRegularlyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("exerciseRegularly", "idexists", "A new exerciseRegularly cannot already have an ID")).body(null);
        }

        ExerciseRegularlyDTO result = exerciseRegularlyService.save(exerciseRegularlyDTO);
        return ResponseEntity.created(new URI("/api/exercise-regularlies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("exerciseRegularly", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exercise-regularlies : Updates an existing exerciseRegularly.
     *
     * @param exerciseRegularlyDTO the exerciseRegularlyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exerciseRegularlyDTO,
     * or with status 400 (Bad Request) if the exerciseRegularlyDTO is not valid,
     * or with status 500 (Internal Server Error) if the exerciseRegularlyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/exercise-regularlies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseRegularlyDTO> updateExerciseRegularly(@Valid @RequestBody ExerciseRegularlyDTO exerciseRegularlyDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseRegularly : {}", exerciseRegularlyDTO);
        if (exerciseRegularlyDTO.getId() == null) {
            return createExerciseRegularly(exerciseRegularlyDTO);
        }
        ExerciseRegularlyDTO result = exerciseRegularlyService.save(exerciseRegularlyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("exerciseRegularly", exerciseRegularlyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exercise-regularlies : get all the exerciseRegularlies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of exerciseRegularlies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/exercise-regularlies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ExerciseRegularlyDTO>> getAllExerciseRegularlies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ExerciseRegularlies");
        Page<ExerciseRegularly> page = exerciseRegularlyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exercise-regularlies");
        return new ResponseEntity<>(exerciseRegularlyMapper.exerciseRegularliesToExerciseRegularlyDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /exercise-regularlies/:id : get the "id" exerciseRegularly.
     *
     * @param id the id of the exerciseRegularlyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exerciseRegularlyDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/exercise-regularlies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExerciseRegularlyDTO> getExerciseRegularly(@PathVariable Long id) {
        log.debug("REST request to get ExerciseRegularly : {}", id);
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyService.findOne(id);
        return Optional.ofNullable(exerciseRegularlyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /exercise-regularlies/:id : delete the "id" exerciseRegularly.
     *
     * @param id the id of the exerciseRegularlyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/exercise-regularlies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExerciseRegularly(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseRegularly : {}", id);
        exerciseRegularlyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("exerciseRegularly", id.toString())).build();
    }

}
