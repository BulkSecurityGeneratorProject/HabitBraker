package org.habitbraker.service;

import org.habitbraker.domain.ExerciseRegularly;
import org.habitbraker.web.rest.dto.ExerciseRegularlyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ExerciseRegularly.
 */
public interface ExerciseRegularlyService {

    /**
     * Save a exerciseRegularly.
     * 
     * @param exerciseRegularlyDTO the entity to save
     * @return the persisted entity
     */
    ExerciseRegularlyDTO save(ExerciseRegularlyDTO exerciseRegularlyDTO);

    /**
     *  Get all the exerciseRegularlies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExerciseRegularly> findAll(Pageable pageable);

    /**
     *  Get the "id" exerciseRegularly.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ExerciseRegularlyDTO findOne(Long id);

    /**
     *  Delete the "id" exerciseRegularly.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
