package org.habitbraker.service.impl;

import org.habitbraker.service.YourProgressService;
import org.habitbraker.domain.YourProgress;
import org.habitbraker.repository.YourProgressRepository;
import org.habitbraker.web.rest.dto.YourProgressDTO;
import org.habitbraker.web.rest.mapper.YourProgressMapper;
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
 * Service Implementation for managing YourProgress.
 */
@Service
@Transactional
public class YourProgressServiceImpl implements YourProgressService{

    private final Logger log = LoggerFactory.getLogger(YourProgressServiceImpl.class);
    
    @Inject
    private YourProgressRepository yourProgressRepository;
    
    @Inject
    private YourProgressMapper yourProgressMapper;
    
    /**
     * Save a yourProgress.
     * 
     * @param yourProgressDTO the entity to save
     * @return the persisted entity
     */
    public YourProgressDTO save(YourProgressDTO yourProgressDTO) {
        log.debug("Request to save YourProgress : {}", yourProgressDTO);
        YourProgress yourProgress = yourProgressMapper.yourProgressDTOToYourProgress(yourProgressDTO);
        yourProgress = yourProgressRepository.save(yourProgress);
        YourProgressDTO result = yourProgressMapper.yourProgressToYourProgressDTO(yourProgress);
        return result;
    }

    /**
     *  Get all the yourProgresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<YourProgress> findAll(Pageable pageable) {
        log.debug("Request to get all YourProgresses");
        Page<YourProgress> result = yourProgressRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one yourProgress by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public YourProgressDTO findOne(Long id) {
        log.debug("Request to get YourProgress : {}", id);
        YourProgress yourProgress = yourProgressRepository.findOne(id);
        YourProgressDTO yourProgressDTO = yourProgressMapper.yourProgressToYourProgressDTO(yourProgress);
        return yourProgressDTO;
    }

    /**
     *  Delete the  yourProgress by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete YourProgress : {}", id);
        yourProgressRepository.delete(id);
    }
}
