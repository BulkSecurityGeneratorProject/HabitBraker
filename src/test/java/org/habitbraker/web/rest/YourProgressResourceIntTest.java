package org.habitbraker.web.rest;

import org.habitbraker.HabitBrakerApp;
import org.habitbraker.domain.YourProgress;
import org.habitbraker.repository.YourProgressRepository;
import org.habitbraker.service.YourProgressService;
import org.habitbraker.web.rest.dto.YourProgressDTO;
import org.habitbraker.web.rest.mapper.YourProgressMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the YourProgressResource REST controller.
 *
 * @see YourProgressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HabitBrakerApp.class)
@WebAppConfiguration
@IntegrationTest
public class YourProgressResourceIntTest {


    private static final Boolean DEFAULT_MET_GOAL_TODAY = false;
    private static final Boolean UPDATED_MET_GOAL_TODAY = true;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private YourProgressRepository yourProgressRepository;

    @Inject
    private YourProgressMapper yourProgressMapper;

    @Inject
    private YourProgressService yourProgressService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYourProgressMockMvc;

    private YourProgress yourProgress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YourProgressResource yourProgressResource = new YourProgressResource();
        ReflectionTestUtils.setField(yourProgressResource, "yourProgressService", yourProgressService);
        ReflectionTestUtils.setField(yourProgressResource, "yourProgressMapper", yourProgressMapper);
        this.restYourProgressMockMvc = MockMvcBuilders.standaloneSetup(yourProgressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yourProgress = new YourProgress();
        yourProgress.setMetGoalToday(DEFAULT_MET_GOAL_TODAY);
        yourProgress.setDate(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createYourProgress() throws Exception {
        int databaseSizeBeforeCreate = yourProgressRepository.findAll().size();

        // Create the YourProgress
        YourProgressDTO yourProgressDTO = yourProgressMapper.yourProgressToYourProgressDTO(yourProgress);

        restYourProgressMockMvc.perform(post("/api/your-progresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yourProgressDTO)))
                .andExpect(status().isCreated());

        // Validate the YourProgress in the database
        List<YourProgress> yourProgresses = yourProgressRepository.findAll();
        assertThat(yourProgresses).hasSize(databaseSizeBeforeCreate + 1);
        YourProgress testYourProgress = yourProgresses.get(yourProgresses.size() - 1);
        assertThat(testYourProgress.isMetGoalToday()).isEqualTo(DEFAULT_MET_GOAL_TODAY);
        assertThat(testYourProgress.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void checkMetGoalTodayIsRequired() throws Exception {
        int databaseSizeBeforeTest = yourProgressRepository.findAll().size();
        // set the field null
        yourProgress.setMetGoalToday(null);

        // Create the YourProgress, which fails.
        YourProgressDTO yourProgressDTO = yourProgressMapper.yourProgressToYourProgressDTO(yourProgress);

        restYourProgressMockMvc.perform(post("/api/your-progresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yourProgressDTO)))
                .andExpect(status().isBadRequest());

        List<YourProgress> yourProgresses = yourProgressRepository.findAll();
        assertThat(yourProgresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yourProgressRepository.findAll().size();
        // set the field null
        yourProgress.setDate(null);

        // Create the YourProgress, which fails.
        YourProgressDTO yourProgressDTO = yourProgressMapper.yourProgressToYourProgressDTO(yourProgress);

        restYourProgressMockMvc.perform(post("/api/your-progresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yourProgressDTO)))
                .andExpect(status().isBadRequest());

        List<YourProgress> yourProgresses = yourProgressRepository.findAll();
        assertThat(yourProgresses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYourProgresses() throws Exception {
        // Initialize the database
        yourProgressRepository.saveAndFlush(yourProgress);

        // Get all the yourProgresses
        restYourProgressMockMvc.perform(get("/api/your-progresses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yourProgress.getId().intValue())))
                .andExpect(jsonPath("$.[*].metGoalToday").value(hasItem(DEFAULT_MET_GOAL_TODAY.booleanValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getYourProgress() throws Exception {
        // Initialize the database
        yourProgressRepository.saveAndFlush(yourProgress);

        // Get the yourProgress
        restYourProgressMockMvc.perform(get("/api/your-progresses/{id}", yourProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yourProgress.getId().intValue()))
            .andExpect(jsonPath("$.metGoalToday").value(DEFAULT_MET_GOAL_TODAY.booleanValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYourProgress() throws Exception {
        // Get the yourProgress
        restYourProgressMockMvc.perform(get("/api/your-progresses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYourProgress() throws Exception {
        // Initialize the database
        yourProgressRepository.saveAndFlush(yourProgress);
        int databaseSizeBeforeUpdate = yourProgressRepository.findAll().size();

        // Update the yourProgress
        YourProgress updatedYourProgress = new YourProgress();
        updatedYourProgress.setId(yourProgress.getId());
        updatedYourProgress.setMetGoalToday(UPDATED_MET_GOAL_TODAY);
        updatedYourProgress.setDate(UPDATED_DATE);
        YourProgressDTO yourProgressDTO = yourProgressMapper.yourProgressToYourProgressDTO(updatedYourProgress);

        restYourProgressMockMvc.perform(put("/api/your-progresses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yourProgressDTO)))
                .andExpect(status().isOk());

        // Validate the YourProgress in the database
        List<YourProgress> yourProgresses = yourProgressRepository.findAll();
        assertThat(yourProgresses).hasSize(databaseSizeBeforeUpdate);
        YourProgress testYourProgress = yourProgresses.get(yourProgresses.size() - 1);
        assertThat(testYourProgress.isMetGoalToday()).isEqualTo(UPDATED_MET_GOAL_TODAY);
        assertThat(testYourProgress.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void deleteYourProgress() throws Exception {
        // Initialize the database
        yourProgressRepository.saveAndFlush(yourProgress);
        int databaseSizeBeforeDelete = yourProgressRepository.findAll().size();

        // Get the yourProgress
        restYourProgressMockMvc.perform(delete("/api/your-progresses/{id}", yourProgress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YourProgress> yourProgresses = yourProgressRepository.findAll();
        assertThat(yourProgresses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
