package org.habitbraker.service;

import org.habitbraker.domain.YourProgress;
import org.habitbraker.web.rest.dto.YourProgressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing YourProgress.
 */
public interface YourProgressService {

    /**
     * Save a yourProgress.
     * 
     * @param yourProgressDTO the entity to save
     * @return the persisted entity
     */
    YourProgressDTO save(YourProgressDTO yourProgressDTO);

    /**
     *  Get all the yourProgresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<YourProgress> findAll(Pageable pageable);

    /**
     *  Get the "id" yourProgress.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    YourProgressDTO findOne(Long id);

    /**
     *  Delete the "id" yourProgress.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
