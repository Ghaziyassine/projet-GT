package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PhotoTestSamples.*;
import static com.mycompany.myapp.domain.TerrainTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Photo.class);
        Photo photo1 = getPhotoSample1();
        Photo photo2 = new Photo();
        assertThat(photo1).isNotEqualTo(photo2);

        photo2.setId(photo1.getId());
        assertThat(photo1).isEqualTo(photo2);

        photo2 = getPhotoSample2();
        assertThat(photo1).isNotEqualTo(photo2);
    }

    @Test
    void nomTerrainTest() throws Exception {
        Photo photo = getPhotoRandomSampleGenerator();
        Terrain terrainBack = getTerrainRandomSampleGenerator();

        photo.setNomTerrain(terrainBack);
        assertThat(photo.getNomTerrain()).isEqualTo(terrainBack);

        photo.nomTerrain(null);
        assertThat(photo.getNomTerrain()).isNull();
    }
}
