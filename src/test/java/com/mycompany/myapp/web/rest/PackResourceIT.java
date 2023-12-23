package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Pack;
import com.mycompany.myapp.repository.PackRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link PackResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PackResourceIT {

    private static final String DEFAULT_NOM_PACK = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PACK = "BBBBBBBBBB";

    private static final String DEFAULT_TARIF = "AAAAAAAAAA";
    private static final String UPDATED_TARIF = "BBBBBBBBBB";

    private static final Long DEFAULT_NBR_DE_MATCHES = 1L;
    private static final Long UPDATED_NBR_DE_MATCHES = 2L;

    private static final String ENTITY_API_URL = "/api/packs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackMockMvc;

    private Pack pack;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pack createEntity(EntityManager em) {
        Pack pack = new Pack().nomPack(DEFAULT_NOM_PACK).tarif(DEFAULT_TARIF).nbrDeMatches(DEFAULT_NBR_DE_MATCHES);
        return pack;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pack createUpdatedEntity(EntityManager em) {
        Pack pack = new Pack().nomPack(UPDATED_NOM_PACK).tarif(UPDATED_TARIF).nbrDeMatches(UPDATED_NBR_DE_MATCHES);
        return pack;
    }

    @BeforeEach
    public void initTest() {
        pack = createEntity(em);
    }

    @Test
    @Transactional
    void createPack() throws Exception {
        int databaseSizeBeforeCreate = packRepository.findAll().size();
        // Create the Pack
        restPackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isCreated());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeCreate + 1);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getNomPack()).isEqualTo(DEFAULT_NOM_PACK);
        assertThat(testPack.getTarif()).isEqualTo(DEFAULT_TARIF);
        assertThat(testPack.getNbrDeMatches()).isEqualTo(DEFAULT_NBR_DE_MATCHES);
    }

    @Test
    @Transactional
    void createPackWithExistingId() throws Exception {
        // Create the Pack with an existing ID
        pack.setId(1L);

        int databaseSizeBeforeCreate = packRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPacks() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        // Get all the packList
        restPackMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pack.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPack").value(hasItem(DEFAULT_NOM_PACK)))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF)))
            .andExpect(jsonPath("$.[*].nbrDeMatches").value(hasItem(DEFAULT_NBR_DE_MATCHES.intValue())));
    }

    @Test
    @Transactional
    void getPack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        // Get the pack
        restPackMockMvc
            .perform(get(ENTITY_API_URL_ID, pack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pack.getId().intValue()))
            .andExpect(jsonPath("$.nomPack").value(DEFAULT_NOM_PACK))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF))
            .andExpect(jsonPath("$.nbrDeMatches").value(DEFAULT_NBR_DE_MATCHES.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPack() throws Exception {
        // Get the pack
        restPackMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Update the pack
        Pack updatedPack = packRepository.findById(pack.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPack are not directly saved in db
        em.detach(updatedPack);
        updatedPack.nomPack(UPDATED_NOM_PACK).tarif(UPDATED_TARIF).nbrDeMatches(UPDATED_NBR_DE_MATCHES);

        restPackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPack.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPack))
            )
            .andExpect(status().isOk());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getNomPack()).isEqualTo(UPDATED_NOM_PACK);
        assertThat(testPack.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testPack.getNbrDeMatches()).isEqualTo(UPDATED_NBR_DE_MATCHES);
    }

    @Test
    @Transactional
    void putNonExistingPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();
        pack.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pack.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pack))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();
        pack.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pack))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();
        pack.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePackWithPatch() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Update the pack using partial update
        Pack partialUpdatedPack = new Pack();
        partialUpdatedPack.setId(pack.getId());

        restPackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPack))
            )
            .andExpect(status().isOk());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getNomPack()).isEqualTo(DEFAULT_NOM_PACK);
        assertThat(testPack.getTarif()).isEqualTo(DEFAULT_TARIF);
        assertThat(testPack.getNbrDeMatches()).isEqualTo(DEFAULT_NBR_DE_MATCHES);
    }

    @Test
    @Transactional
    void fullUpdatePackWithPatch() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Update the pack using partial update
        Pack partialUpdatedPack = new Pack();
        partialUpdatedPack.setId(pack.getId());

        partialUpdatedPack.nomPack(UPDATED_NOM_PACK).tarif(UPDATED_TARIF).nbrDeMatches(UPDATED_NBR_DE_MATCHES);

        restPackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPack))
            )
            .andExpect(status().isOk());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getNomPack()).isEqualTo(UPDATED_NOM_PACK);
        assertThat(testPack.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testPack.getNbrDeMatches()).isEqualTo(UPDATED_NBR_DE_MATCHES);
    }

    @Test
    @Transactional
    void patchNonExistingPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();
        pack.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pack.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pack))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();
        pack.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pack))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();
        pack.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeDelete = packRepository.findAll().size();

        // Delete the pack
        restPackMockMvc
            .perform(delete(ENTITY_API_URL_ID, pack.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
