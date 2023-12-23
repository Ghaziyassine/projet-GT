package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClubTestSamples.*;
import static com.mycompany.myapp.domain.PhotoTestSamples.*;
import static com.mycompany.myapp.domain.ReservationTerrainTestSamples.*;
import static com.mycompany.myapp.domain.TerrainTestSamples.*;
import static com.mycompany.myapp.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TerrainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terrain.class);
        Terrain terrain1 = getTerrainSample1();
        Terrain terrain2 = new Terrain();
        assertThat(terrain1).isNotEqualTo(terrain2);

        terrain2.setId(terrain1.getId());
        assertThat(terrain1).isEqualTo(terrain2);

        terrain2 = getTerrainSample2();
        assertThat(terrain1).isNotEqualTo(terrain2);
    }

    @Test
    void photoTest() throws Exception {
        Terrain terrain = getTerrainRandomSampleGenerator();
        Photo photoBack = getPhotoRandomSampleGenerator();

        terrain.addPhoto(photoBack);
        assertThat(terrain.getPhotos()).containsOnly(photoBack);
        assertThat(photoBack.getNomTerrain()).isEqualTo(terrain);

        terrain.removePhoto(photoBack);
        assertThat(terrain.getPhotos()).doesNotContain(photoBack);
        assertThat(photoBack.getNomTerrain()).isNull();

        terrain.photos(new HashSet<>(Set.of(photoBack)));
        assertThat(terrain.getPhotos()).containsOnly(photoBack);
        assertThat(photoBack.getNomTerrain()).isEqualTo(terrain);

        terrain.setPhotos(new HashSet<>());
        assertThat(terrain.getPhotos()).doesNotContain(photoBack);
        assertThat(photoBack.getNomTerrain()).isNull();
    }

    @Test
    void reservationTerrainTest() throws Exception {
        Terrain terrain = getTerrainRandomSampleGenerator();
        ReservationTerrain reservationTerrainBack = getReservationTerrainRandomSampleGenerator();

        terrain.addReservationTerrain(reservationTerrainBack);
        assertThat(terrain.getReservationTerrains()).containsOnly(reservationTerrainBack);
        assertThat(reservationTerrainBack.getNomTerrain()).isEqualTo(terrain);

        terrain.removeReservationTerrain(reservationTerrainBack);
        assertThat(terrain.getReservationTerrains()).doesNotContain(reservationTerrainBack);
        assertThat(reservationTerrainBack.getNomTerrain()).isNull();

        terrain.reservationTerrains(new HashSet<>(Set.of(reservationTerrainBack)));
        assertThat(terrain.getReservationTerrains()).containsOnly(reservationTerrainBack);
        assertThat(reservationTerrainBack.getNomTerrain()).isEqualTo(terrain);

        terrain.setReservationTerrains(new HashSet<>());
        assertThat(terrain.getReservationTerrains()).doesNotContain(reservationTerrainBack);
        assertThat(reservationTerrainBack.getNomTerrain()).isNull();
    }

    @Test
    void nomClubTest() throws Exception {
        Terrain terrain = getTerrainRandomSampleGenerator();
        Club clubBack = getClubRandomSampleGenerator();

        terrain.setNomClub(clubBack);
        assertThat(terrain.getNomClub()).isEqualTo(clubBack);

        terrain.nomClub(null);
        assertThat(terrain.getNomClub()).isNull();
    }

    @Test
    void nomZoneTest() throws Exception {
        Terrain terrain = getTerrainRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        terrain.setNomZone(zoneBack);
        assertThat(terrain.getNomZone()).isEqualTo(zoneBack);

        terrain.nomZone(null);
        assertThat(terrain.getNomZone()).isNull();
    }
}
