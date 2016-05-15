package org.habitbraker.service.impl;

import org.habitbraker.service.ExerciseRegularlyService;
import org.habitbraker.domain.ExerciseRegularly;
import org.habitbraker.repository.ExerciseRegularlyRepository;
import org.habitbraker.web.rest.dto.ExerciseRegularlyDTO;
import org.habitbraker.web.rest.mapper.ExerciseRegularlyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ExerciseRegularly.
 */
@Service
@Transactional
public class ExerciseRegularlyServiceImpl implements ExerciseRegularlyService{

    private final Logger log = LoggerFactory.getLogger(ExerciseRegularlyServiceImpl.class);
    
    @Inject
    private ExerciseRegularlyRepository exerciseRegularlyRepository;
    
    @Inject
    private ExerciseRegularlyMapper exerciseRegularlyMapper;
    
    /**
     * Save a exerciseRegularly.
     * 
     * @param exerciseRegularlyDTO the entity to save
     * @return the persisted entity
     */
    public ExerciseRegularlyDTO save(ExerciseRegularlyDTO exerciseRegularlyDTO) {
        log.debug("Request to save ExerciseRegularly : {}", exerciseRegularlyDTO);
        ExerciseRegularly exerciseRegularly = exerciseRegularlyMapper.exerciseRegularlyDTOToExerciseRegularly(exerciseRegularlyDTO);
        exerciseRegularly = exerciseRegularlyRepository.save(exerciseRegularly);
        ExerciseRegularlyDTO result = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);
        return result;
    }

    /**
     *  Get all the exerciseRegularlies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ExerciseRegularly> findAll(Pageable pageable) {
        log.debug("Request to get all ExerciseRegularlies");
        Page<ExerciseRegularly> result = exerciseRegularlyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one exerciseRegularly by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ExerciseRegularlyDTO findOne(Long id) {
        log.debug("Request to get ExerciseRegularly : {}", id);
        ExerciseRegularly exerciseRegularly = exerciseRegularlyRepository.findOne(id);
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);
        return exerciseRegularlyDTO;
    }

    /**
     *  Delete the  exerciseRegularly by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExerciseRegularly : {}", id);
        exerciseRegularlyRepository.delete(id);
    }
}
