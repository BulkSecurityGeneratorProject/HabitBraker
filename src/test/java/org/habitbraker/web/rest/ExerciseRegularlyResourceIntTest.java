package org.habitbraker.web.rest;

import org.habitbraker.HabitBrakerApp;
import org.habitbraker.domain.ExerciseRegularly;
import org.habitbraker.repository.ExerciseRegularlyRepository;
import org.habitbraker.service.ExerciseRegularlyService;
import org.habitbraker.web.rest.dto.ExerciseRegularlyDTO;
import org.habitbraker.web.rest.mapper.ExerciseRegularlyMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.habitbraker.domain.enumeration.Charities;

/**
 * Test class for the ExerciseRegularlyResource REST controller.
 *
 * @see ExerciseRegularlyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HabitBrakerApp.class)
@WebAppConfiguration
@IntegrationTest
public class ExerciseRegularlyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_COMMITTED_TO = "AAAAA";
    private static final String UPDATED_COMMITTED_TO = "BBBBB";

    private static final Double DEFAULT_PLEDGED_AMMOUNT = 1D;
    private static final Double UPDATED_PLEDGED_AMMOUNT = 2D;

    private static final Charities DEFAULT_PLEDGED_CHARITY = Charities.CureKids;
    private static final Charities UPDATED_PLEDGED_CHARITY = Charities.SPCA;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);
    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";

    @Inject
    private ExerciseRegularlyRepository exerciseRegularlyRepository;

    @Inject
    private ExerciseRegularlyMapper exerciseRegularlyMapper;

    @Inject
    private ExerciseRegularlyService exerciseRegularlyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExerciseRegularlyMockMvc;

    private ExerciseRegularly exerciseRegularly;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExerciseRegularlyResource exerciseRegularlyResource = new ExerciseRegularlyResource();
        ReflectionTestUtils.setField(exerciseRegularlyResource, "exerciseRegularlyService", exerciseRegularlyService);
        ReflectionTestUtils.setField(exerciseRegularlyResource, "exerciseRegularlyMapper", exerciseRegularlyMapper);
        this.restExerciseRegularlyMockMvc = MockMvcBuilders.standaloneSetup(exerciseRegularlyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        exerciseRegularly = new ExerciseRegularly();
        exerciseRegularly.setCommittedTo(DEFAULT_COMMITTED_TO);
        exerciseRegularly.setPledgedAmmount(DEFAULT_PLEDGED_AMMOUNT);
        exerciseRegularly.setPledgedCharity(DEFAULT_PLEDGED_CHARITY);
        exerciseRegularly.setStartDate(DEFAULT_START_DATE);
        exerciseRegularly.setEndDate(DEFAULT_END_DATE);
        exerciseRegularly.setNotes(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createExerciseRegularly() throws Exception {
        int databaseSizeBeforeCreate = exerciseRegularlyRepository.findAll().size();

        // Create the ExerciseRegularly
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);

        restExerciseRegularlyMockMvc.perform(post("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isCreated());

        // Validate the ExerciseRegularly in the database
        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseRegularly testExerciseRegularly = exerciseRegularlies.get(exerciseRegularlies.size() - 1);
        assertThat(testExerciseRegularly.getCommittedTo()).isEqualTo(DEFAULT_COMMITTED_TO);
        assertThat(testExerciseRegularly.getPledgedAmmount()).isEqualTo(DEFAULT_PLEDGED_AMMOUNT);
        assertThat(testExerciseRegularly.getPledgedCharity()).isEqualTo(DEFAULT_PLEDGED_CHARITY);
        assertThat(testExerciseRegularly.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExerciseRegularly.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExerciseRegularly.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void checkCommittedToIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRegularlyRepository.findAll().size();
        // set the field null
        exerciseRegularly.setCommittedTo(null);

        // Create the ExerciseRegularly, which fails.
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);

        restExerciseRegularlyMockMvc.perform(post("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isBadRequest());

        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPledgedAmmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRegularlyRepository.findAll().size();
        // set the field null
        exerciseRegularly.setPledgedAmmount(null);

        // Create the ExerciseRegularly, which fails.
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);

        restExerciseRegularlyMockMvc.perform(post("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isBadRequest());

        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPledgedCharityIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRegularlyRepository.findAll().size();
        // set the field null
        exerciseRegularly.setPledgedCharity(null);

        // Create the ExerciseRegularly, which fails.
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);

        restExerciseRegularlyMockMvc.perform(post("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isBadRequest());

        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRegularlyRepository.findAll().size();
        // set the field null
        exerciseRegularly.setStartDate(null);

        // Create the ExerciseRegularly, which fails.
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);

        restExerciseRegularlyMockMvc.perform(post("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isBadRequest());

        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseRegularlyRepository.findAll().size();
        // set the field null
        exerciseRegularly.setEndDate(null);

        // Create the ExerciseRegularly, which fails.
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(exerciseRegularly);

        restExerciseRegularlyMockMvc.perform(post("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isBadRequest());

        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExerciseRegularlies() throws Exception {
        // Initialize the database
        exerciseRegularlyRepository.saveAndFlush(exerciseRegularly);

        // Get all the exerciseRegularlies
        restExerciseRegularlyMockMvc.perform(get("/api/exercise-regularlies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseRegularly.getId().intValue())))
                .andExpect(jsonPath("$.[*].committedTo").value(hasItem(DEFAULT_COMMITTED_TO.toString())))
                .andExpect(jsonPath("$.[*].pledgedAmmount").value(hasItem(DEFAULT_PLEDGED_AMMOUNT.doubleValue())))
                .andExpect(jsonPath("$.[*].pledgedCharity").value(hasItem(DEFAULT_PLEDGED_CHARITY.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getExerciseRegularly() throws Exception {
        // Initialize the database
        exerciseRegularlyRepository.saveAndFlush(exerciseRegularly);

        // Get the exerciseRegularly
        restExerciseRegularlyMockMvc.perform(get("/api/exercise-regularlies/{id}", exerciseRegularly.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(exerciseRegularly.getId().intValue()))
            .andExpect(jsonPath("$.committedTo").value(DEFAULT_COMMITTED_TO.toString()))
            .andExpect(jsonPath("$.pledgedAmmount").value(DEFAULT_PLEDGED_AMMOUNT.doubleValue()))
            .andExpect(jsonPath("$.pledgedCharity").value(DEFAULT_PLEDGED_CHARITY.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExerciseRegularly() throws Exception {
        // Get the exerciseRegularly
        restExerciseRegularlyMockMvc.perform(get("/api/exercise-regularlies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseRegularly() throws Exception {
        // Initialize the database
        exerciseRegularlyRepository.saveAndFlush(exerciseRegularly);
        int databaseSizeBeforeUpdate = exerciseRegularlyRepository.findAll().size();

        // Update the exerciseRegularly
        ExerciseRegularly updatedExerciseRegularly = new ExerciseRegularly();
        updatedExerciseRegularly.setId(exerciseRegularly.getId());
        updatedExerciseRegularly.setCommittedTo(UPDATED_COMMITTED_TO);
        updatedExerciseRegularly.setPledgedAmmount(UPDATED_PLEDGED_AMMOUNT);
        updatedExerciseRegularly.setPledgedCharity(UPDATED_PLEDGED_CHARITY);
        updatedExerciseRegularly.setStartDate(UPDATED_START_DATE);
        updatedExerciseRegularly.setEndDate(UPDATED_END_DATE);
        updatedExerciseRegularly.setNotes(UPDATED_NOTES);
        ExerciseRegularlyDTO exerciseRegularlyDTO = exerciseRegularlyMapper.exerciseRegularlyToExerciseRegularlyDTO(updatedExerciseRegularly);

        restExerciseRegularlyMockMvc.perform(put("/api/exercise-regularlies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exerciseRegularlyDTO)))
                .andExpect(status().isOk());

        // Validate the ExerciseRegularly in the database
        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeUpdate);
        ExerciseRegularly testExerciseRegularly = exerciseRegularlies.get(exerciseRegularlies.size() - 1);
        assertThat(testExerciseRegularly.getCommittedTo()).isEqualTo(UPDATED_COMMITTED_TO);
        assertThat(testExerciseRegularly.getPledgedAmmount()).isEqualTo(UPDATED_PLEDGED_AMMOUNT);
        assertThat(testExerciseRegularly.getPledgedCharity()).isEqualTo(UPDATED_PLEDGED_CHARITY);
        assertThat(testExerciseRegularly.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExerciseRegularly.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExerciseRegularly.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void deleteExerciseRegularly() throws Exception {
        // Initialize the database
        exerciseRegularlyRepository.saveAndFlush(exerciseRegularly);
        int databaseSizeBeforeDelete = exerciseRegularlyRepository.findAll().size();

        // Get the exerciseRegularly
        restExerciseRegularlyMockMvc.perform(delete("/api/exercise-regularlies/{id}", exerciseRegularly.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExerciseRegularly> exerciseRegularlies = exerciseRegularlyRepository.findAll();
        assertThat(exerciseRegularlies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
