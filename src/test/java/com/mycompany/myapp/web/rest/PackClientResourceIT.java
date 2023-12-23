package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PackClient;
import com.mycompany.myapp.repository.PackClientRepository;
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
 * Integration tests for the {@link PackClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PackClientResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/pack-clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PackClientRepository packClientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackClientMockMvc;

    private PackClient packClient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackClient createEntity(EntityManager em) {
        PackClient packClient = new PackClient().date(DEFAULT_DATE);
        return packClient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackClient createUpdatedEntity(EntityManager em) {
        PackClient packClient = new PackClient().date(UPDATED_DATE);
        return packClient;
    }

    @BeforeEach
    public void initTest() {
        packClient = createEntity(em);
    }

    @Test
    @Transactional
    void createPackClient() throws Exception {
        int databaseSizeBeforeCreate = packClientRepository.findAll().size();
        // Create the PackClient
        restPackClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packClient)))
            .andExpect(status().isCreated());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeCreate + 1);
        PackClient testPackClient = packClientList.get(packClientList.size() - 1);
        assertThat(testPackClient.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createPackClientWithExistingId() throws Exception {
        // Create the PackClient with an existing ID
        packClient.setId(1L);

        int databaseSizeBeforeCreate = packClientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packClient)))
            .andExpect(status().isBadRequest());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPackClients() throws Exception {
        // Initialize the database
        packClientRepository.saveAndFlush(packClient);

        // Get all the packClientList
        restPackClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packClient.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getPackClient() throws Exception {
        // Initialize the database
        packClientRepository.saveAndFlush(packClient);

        // Get the packClient
        restPackClientMockMvc
            .perform(get(ENTITY_API_URL_ID, packClient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packClient.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPackClient() throws Exception {
        // Get the packClient
        restPackClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPackClient() throws Exception {
        // Initialize the database
        packClientRepository.saveAndFlush(packClient);

        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();

        // Update the packClient
        PackClient updatedPackClient = packClientRepository.findById(packClient.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPackClient are not directly saved in db
        em.detach(updatedPackClient);
        updatedPackClient.date(UPDATED_DATE);

        restPackClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPackClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPackClient))
            )
            .andExpect(status().isOk());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
        PackClient testPackClient = packClientList.get(packClientList.size() - 1);
        assertThat(testPackClient.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPackClient() throws Exception {
        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();
        packClient.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packClient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPackClient() throws Exception {
        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();
        packClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPackClient() throws Exception {
        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();
        packClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(packClient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePackClientWithPatch() throws Exception {
        // Initialize the database
        packClientRepository.saveAndFlush(packClient);

        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();

        // Update the packClient using partial update
        PackClient partialUpdatedPackClient = new PackClient();
        partialUpdatedPackClient.setId(packClient.getId());

        restPackClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackClient))
            )
            .andExpect(status().isOk());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
        PackClient testPackClient = packClientList.get(packClientList.size() - 1);
        assertThat(testPackClient.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePackClientWithPatch() throws Exception {
        // Initialize the database
        packClientRepository.saveAndFlush(packClient);

        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();

        // Update the packClient using partial update
        PackClient partialUpdatedPackClient = new PackClient();
        partialUpdatedPackClient.setId(packClient.getId());

        partialUpdatedPackClient.date(UPDATED_DATE);

        restPackClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackClient))
            )
            .andExpect(status().isOk());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
        PackClient testPackClient = packClientList.get(packClientList.size() - 1);
        assertThat(testPackClient.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPackClient() throws Exception {
        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();
        packClient.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, packClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPackClient() throws Exception {
        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();
        packClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packClient))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPackClient() throws Exception {
        int databaseSizeBeforeUpdate = packClientRepository.findAll().size();
        packClient.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackClientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(packClient))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackClient in the database
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePackClient() throws Exception {
        // Initialize the database
        packClientRepository.saveAndFlush(packClient);

        int databaseSizeBeforeDelete = packClientRepository.findAll().size();

        // Delete the packClient
        restPackClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, packClient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackClient> packClientList = packClientRepository.findAll();
        assertThat(packClientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
