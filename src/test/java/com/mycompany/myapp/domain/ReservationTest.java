package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClientTestSamples.*;
import static com.mycompany.myapp.domain.ReservationTerrainTestSamples.*;
import static com.mycompany.myapp.domain.ReservationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservation.class);
        Reservation reservation1 = getReservationSample1();
        Reservation reservation2 = new Reservation();
        assertThat(reservation1).isNotEqualTo(reservation2);

        reservation2.setId(reservation1.getId());
        assertThat(reservation1).isEqualTo(reservation2);

        reservation2 = getReservationSample2();
        assertThat(reservation1).isNotEqualTo(reservation2);
    }

    @Test
    void reservationTerrainTest() throws Exception {
        Reservation reservation = getReservationRandomSampleGenerator();
        ReservationTerrain reservationTerrainBack = getReservationTerrainRandomSampleGenerator();

        reservation.addReservationTerrain(reservationTerrainBack);
        assertThat(reservation.getReservationTerrains()).containsOnly(reservationTerrainBack);
        assertThat(reservationTerrainBack.getReservation()).isEqualTo(reservation);

        reservation.removeReservationTerrain(reservationTerrainBack);
        assertThat(reservation.getReservationTerrains()).doesNotContain(reservationTerrainBack);
        assertThat(reservationTerrainBack.getReservation()).isNull();

        reservation.reservationTerrains(new HashSet<>(Set.of(reservationTerrainBack)));
        assertThat(reservation.getReservationTerrains()).containsOnly(reservationTerrainBack);
        assertThat(reservationTerrainBack.getReservation()).isEqualTo(reservation);

        reservation.setReservationTerrains(new HashSet<>());
        assertThat(reservation.getReservationTerrains()).doesNotContain(reservationTerrainBack);
        assertThat(reservationTerrainBack.getReservation()).isNull();
    }

    @Test
    void nomClientTest() throws Exception {
        Reservation reservation = getReservationRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        reservation.setNomClient(clientBack);
        assertThat(reservation.getNomClient()).isEqualTo(clientBack);

        reservation.nomClient(null);
        assertThat(reservation.getNomClient()).isNull();
    }
}
