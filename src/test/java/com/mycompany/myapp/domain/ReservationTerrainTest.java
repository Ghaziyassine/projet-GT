package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ReservationTerrainTestSamples.*;
import static com.mycompany.myapp.domain.ReservationTestSamples.*;
import static com.mycompany.myapp.domain.TerrainTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservationTerrainTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReservationTerrain.class);
        ReservationTerrain reservationTerrain1 = getReservationTerrainSample1();
        ReservationTerrain reservationTerrain2 = new ReservationTerrain();
        assertThat(reservationTerrain1).isNotEqualTo(reservationTerrain2);

        reservationTerrain2.setId(reservationTerrain1.getId());
        assertThat(reservationTerrain1).isEqualTo(reservationTerrain2);

        reservationTerrain2 = getReservationTerrainSample2();
        assertThat(reservationTerrain1).isNotEqualTo(reservationTerrain2);
    }

    @Test
    void reservationTest() throws Exception {
        ReservationTerrain reservationTerrain = getReservationTerrainRandomSampleGenerator();
        Reservation reservationBack = getReservationRandomSampleGenerator();

        reservationTerrain.setReservation(reservationBack);
        assertThat(reservationTerrain.getReservation()).isEqualTo(reservationBack);

        reservationTerrain.reservation(null);
        assertThat(reservationTerrain.getReservation()).isNull();
    }

    @Test
    void nomTerrainTest() throws Exception {
        ReservationTerrain reservationTerrain = getReservationTerrainRandomSampleGenerator();
        Terrain terrainBack = getTerrainRandomSampleGenerator();

        reservationTerrain.setNomTerrain(terrainBack);
        assertThat(reservationTerrain.getNomTerrain()).isEqualTo(terrainBack);

        reservationTerrain.nomTerrain(null);
        assertThat(reservationTerrain.getNomTerrain()).isNull();
    }
}
