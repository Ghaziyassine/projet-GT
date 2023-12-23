package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Terrain;
import com.mycompany.myapp.repository.TerrainRepository;
import jakarta.persistence.EntityManager;
import java.util.Base64;
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
 * Integration tests for the {@link TerrainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TerrainResourceIT {

    private static final String DEFAULT_NOM_TERRAIN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TERRAIN = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Long DEFAULT_RANK = 1L;
    private static final Long UPDATED_RANK = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_SAL = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SAL = "BBBBBBBBBB";

    private static final String DEFAULT_TARIF = "AAAAAAAAAA";
    private static final String UPDATED_TARIF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/terrains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TerrainRepository terrainRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTerrainMockMvc;

    private Terrain terrain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terrain createEntity(EntityManager em) {
        Terrain terrain = new Terrain()
            .nomTerrain(DEFAULT_NOM_TERRAIN)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .adresse(DEFAULT_ADRESSE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .rank(DEFAULT_RANK)
            .type(DEFAULT_TYPE)
            .etat(DEFAULT_ETAT)
            .description(DEFAULT_DESCRIPTION)
            .typeSal(DEFAULT_TYPE_SAL)
            .tarif(DEFAULT_TARIF);
        return terrain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Terrain createUpdatedEntity(EntityManager em) {
        Terrain terrain = new Terrain()
            .nomTerrain(UPDATED_NOM_TERRAIN)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .adresse(UPDATED_ADRESSE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .rank(UPDATED_RANK)
            .type(UPDATED_TYPE)
            .etat(UPDATED_ETAT)
            .description(UPDATED_DESCRIPTION)
            .typeSal(UPDATED_TYPE_SAL)
            .tarif(UPDATED_TARIF);
        return terrain;
    }

    @BeforeEach
    public void initTest() {
        terrain = createEntity(em);
    }

    @Test
    @Transactional
    void createTerrain() throws Exception {
        int databaseSizeBeforeCreate = terrainRepository.findAll().size();
        // Create the Terrain
        restTerrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isCreated());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeCreate + 1);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getNomTerrain()).isEqualTo(DEFAULT_NOM_TERRAIN);
        assertThat(testTerrain.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testTerrain.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testTerrain.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testTerrain.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testTerrain.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testTerrain.getRank()).isEqualTo(DEFAULT_RANK);
        assertThat(testTerrain.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTerrain.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testTerrain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTerrain.getTypeSal()).isEqualTo(DEFAULT_TYPE_SAL);
        assertThat(testTerrain.getTarif()).isEqualTo(DEFAULT_TARIF);
    }

    @Test
    @Transactional
    void createTerrainWithExistingId() throws Exception {
        // Create the Terrain with an existing ID
        terrain.setId(1L);

        int databaseSizeBeforeCreate = terrainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTerrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isBadRequest());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTerrains() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get all the terrainList
        restTerrainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(terrain.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTerrain").value(hasItem(DEFAULT_NOM_TERRAIN)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].rank").value(hasItem(DEFAULT_RANK.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].typeSal").value(hasItem(DEFAULT_TYPE_SAL)))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF)));
    }

    @Test
    @Transactional
    void getTerrain() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        // Get the terrain
        restTerrainMockMvc
            .perform(get(ENTITY_API_URL_ID, terrain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(terrain.getId().intValue()))
            .andExpect(jsonPath("$.nomTerrain").value(DEFAULT_NOM_TERRAIN))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64.getEncoder().encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.rank").value(DEFAULT_RANK.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.typeSal").value(DEFAULT_TYPE_SAL))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF));
    }

    @Test
    @Transactional
    void getNonExistingTerrain() throws Exception {
        // Get the terrain
        restTerrainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTerrain() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();

        // Update the terrain
        Terrain updatedTerrain = terrainRepository.findById(terrain.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTerrain are not directly saved in db
        em.detach(updatedTerrain);
        updatedTerrain
            .nomTerrain(UPDATED_NOM_TERRAIN)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .adresse(UPDATED_ADRESSE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .rank(UPDATED_RANK)
            .type(UPDATED_TYPE)
            .etat(UPDATED_ETAT)
            .description(UPDATED_DESCRIPTION)
            .typeSal(UPDATED_TYPE_SAL)
            .tarif(UPDATED_TARIF);

        restTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTerrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTerrain))
            )
            .andExpect(status().isOk());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getNomTerrain()).isEqualTo(UPDATED_NOM_TERRAIN);
        assertThat(testTerrain.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTerrain.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testTerrain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testTerrain.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testTerrain.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testTerrain.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testTerrain.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTerrain.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testTerrain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTerrain.getTypeSal()).isEqualTo(UPDATED_TYPE_SAL);
        assertThat(testTerrain.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    void putNonExistingTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();
        terrain.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, terrain.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();
        terrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(terrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();
        terrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerrainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTerrainWithPatch() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();

        // Update the terrain using partial update
        Terrain partialUpdatedTerrain = new Terrain();
        partialUpdatedTerrain.setId(terrain.getId());

        partialUpdatedTerrain
            .adresse(UPDATED_ADRESSE)
            .longitude(UPDATED_LONGITUDE)
            .rank(UPDATED_RANK)
            .description(UPDATED_DESCRIPTION)
            .typeSal(UPDATED_TYPE_SAL)
            .tarif(UPDATED_TARIF);

        restTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerrain))
            )
            .andExpect(status().isOk());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getNomTerrain()).isEqualTo(DEFAULT_NOM_TERRAIN);
        assertThat(testTerrain.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testTerrain.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testTerrain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testTerrain.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testTerrain.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testTerrain.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testTerrain.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTerrain.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testTerrain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTerrain.getTypeSal()).isEqualTo(UPDATED_TYPE_SAL);
        assertThat(testTerrain.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    void fullUpdateTerrainWithPatch() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();

        // Update the terrain using partial update
        Terrain partialUpdatedTerrain = new Terrain();
        partialUpdatedTerrain.setId(terrain.getId());

        partialUpdatedTerrain
            .nomTerrain(UPDATED_NOM_TERRAIN)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .adresse(UPDATED_ADRESSE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .rank(UPDATED_RANK)
            .type(UPDATED_TYPE)
            .etat(UPDATED_ETAT)
            .description(UPDATED_DESCRIPTION)
            .typeSal(UPDATED_TYPE_SAL)
            .tarif(UPDATED_TARIF);

        restTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTerrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTerrain))
            )
            .andExpect(status().isOk());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
        Terrain testTerrain = terrainList.get(terrainList.size() - 1);
        assertThat(testTerrain.getNomTerrain()).isEqualTo(UPDATED_NOM_TERRAIN);
        assertThat(testTerrain.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTerrain.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testTerrain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testTerrain.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testTerrain.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testTerrain.getRank()).isEqualTo(UPDATED_RANK);
        assertThat(testTerrain.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTerrain.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testTerrain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTerrain.getTypeSal()).isEqualTo(UPDATED_TYPE_SAL);
        assertThat(testTerrain.getTarif()).isEqualTo(UPDATED_TARIF);
    }

    @Test
    @Transactional
    void patchNonExistingTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();
        terrain.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, terrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();
        terrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(terrain))
            )
            .andExpect(status().isBadRequest());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTerrain() throws Exception {
        int databaseSizeBeforeUpdate = terrainRepository.findAll().size();
        terrain.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTerrainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(terrain)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Terrain in the database
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTerrain() throws Exception {
        // Initialize the database
        terrainRepository.saveAndFlush(terrain);

        int databaseSizeBeforeDelete = terrainRepository.findAll().size();

        // Delete the terrain
        restTerrainMockMvc
            .perform(delete(ENTITY_API_URL_ID, terrain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Terrain> terrainList = terrainRepository.findAll();
        assertThat(terrainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
