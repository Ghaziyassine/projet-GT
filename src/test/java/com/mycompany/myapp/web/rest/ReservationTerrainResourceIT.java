package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ReservationTerrain;
import com.mycompany.myapp.repository.ReservationTerrainRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReservationTerrainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservationTerrainResourceIT {

    private static final Integer DEFAULT_HEURE = 1;
    private static final Integer UPDATED_HEURE = 2;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/reservation-terrains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservationTerrainRepository reservationTerrainRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationTerrainMockMvc;

    private ReservationTerrain reservationTerrain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTerrain createEntity(EntityManager em) {
        ReservationTerrain reservationTerrain = new ReservationTerrain().heure(DEFAULT_HEURE).date(DEFAULT_DATE);
        return reservationTerrain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReservationTerrain createUpdatedEntity(EntityManager em) {
        ReservationTerrain reservationTerrain = new ReservationTerrain().heure(UPDATED_HEURE).date(UPDATED_DATE);
        return reservationTerrain;
    }

    @BeforeEach
    public void initTest() {
        reservationTerrain = createEntity(em);
    }

    @Test
    @Transactional
    void createReservationTerrain() throws Exception {
        int databaseSizeBeforeCreate = reservationTerrainRepository.findAll().size();
        // Create the ReservationTerrain
        restReservationTerrainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isCreated());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeCreate + 1);
        ReservationTerrain testReservationTerrain = reservationTerrainList.get(reservationTerrainList.size() - 1);
        assertThat(testReservationTerrain.getHeure()).isEqualTo(DEFAULT_HEURE);
        assertThat(testReservationTerrain.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createReservationTerrainWithExistingId() throws Exception {
        // Create the ReservationTerrain with an existing ID
        reservationTerrain.setId(1L);

        int databaseSizeBeforeCreate = reservationTerrainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationTerrainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReservationTerrains() throws Exception {
        // Initialize the database
        reservationTerrainRepository.saveAndFlush(reservationTerrain);

        // Get all the reservationTerrainList
        restReservationTerrainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservationTerrain.getId().intValue())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getReservationTerrain() throws Exception {
        // Initialize the database
        reservationTerrainRepository.saveAndFlush(reservationTerrain);

        // Get the reservationTerrain
        restReservationTerrainMockMvc
            .perform(get(ENTITY_API_URL_ID, reservationTerrain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservationTerrain.getId().intValue()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReservationTerrain() throws Exception {
        // Get the reservationTerrain
        restReservationTerrainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReservationTerrain() throws Exception {
        // Initialize the database
        reservationTerrainRepository.saveAndFlush(reservationTerrain);

        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();

        // Update the reservationTerrain
        ReservationTerrain updatedReservationTerrain = reservationTerrainRepository.findById(reservationTerrain.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReservationTerrain are not directly saved in db
        em.detach(updatedReservationTerrain);
        updatedReservationTerrain.heure(UPDATED_HEURE).date(UPDATED_DATE);

        restReservationTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReservationTerrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReservationTerrain))
            )
            .andExpect(status().isOk());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
        ReservationTerrain testReservationTerrain = reservationTerrainList.get(reservationTerrainList.size() - 1);
        assertThat(testReservationTerrain.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testReservationTerrain.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingReservationTerrain() throws Exception {
        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();
        reservationTerrain.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationTerrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservationTerrain() throws Exception {
        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();
        reservationTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservationTerrain() throws Exception {
        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();
        reservationTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTerrainMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservationTerrainWithPatch() throws Exception {
        // Initialize the database
        reservationTerrainRepository.saveAndFlush(reservationTerrain);

        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();

        // Update the reservationTerrain using partial update
        ReservationTerrain partialUpdatedReservationTerrain = new ReservationTerrain();
        partialUpdatedReservationTerrain.setId(reservationTerrain.getId());

        partialUpdatedReservationTerrain.heure(UPDATED_HEURE).date(UPDATED_DATE);

        restReservationTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservationTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservationTerrain))
            )
            .andExpect(status().isOk());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
        ReservationTerrain testReservationTerrain = reservationTerrainList.get(reservationTerrainList.size() - 1);
        assertThat(testReservationTerrain.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testReservationTerrain.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateReservationTerrainWithPatch() throws Exception {
        // Initialize the database
        reservationTerrainRepository.saveAndFlush(reservationTerrain);

        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();

        // Update the reservationTerrain using partial update
        ReservationTerrain partialUpdatedReservationTerrain = new ReservationTerrain();
        partialUpdatedReservationTerrain.setId(reservationTerrain.getId());

        partialUpdatedReservationTerrain.heure(UPDATED_HEURE).date(UPDATED_DATE);

        restReservationTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservationTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservationTerrain))
            )
            .andExpect(status().isOk());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
        ReservationTerrain testReservationTerrain = reservationTerrainList.get(reservationTerrainList.size() - 1);
        assertThat(testReservationTerrain.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testReservationTerrain.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingReservationTerrain() throws Exception {
        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();
        reservationTerrain.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservationTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservationTerrain() throws Exception {
        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();
        reservationTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservationTerrain() throws Exception {
        int databaseSizeBeforeUpdate = reservationTerrainRepository.findAll().size();
        reservationTerrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationTerrain))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReservationTerrain in the database
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservationTerrain() throws Exception {
        // Initialize the database
        reservationTerrainRepository.saveAndFlush(reservationTerrain);

        int databaseSizeBeforeDelete = reservationTerrainRepository.findAll().size();

        // Delete the reservationTerrain
        restReservationTerrainMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservationTerrain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReservationTerrain> reservationTerrainList = reservationTerrainRepository.findAll();
        assertThat(reservationTerrainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
