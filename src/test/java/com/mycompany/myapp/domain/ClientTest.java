package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClientTestSamples.*;
import static com.mycompany.myapp.domain.PackClientTestSamples.*;
import static com.mycompany.myapp.domain.ReservationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void packClientTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        PackClient packClientBack = getPackClientRandomSampleGenerator();

        client.addPackClient(packClientBack);
        assertThat(client.getPackClients()).containsOnly(packClientBack);
        assertThat(packClientBack.getNomClient()).isEqualTo(client);

        client.removePackClient(packClientBack);
        assertThat(client.getPackClients()).doesNotContain(packClientBack);
        assertThat(packClientBack.getNomClient()).isNull();

        client.packClients(new HashSet<>(Set.of(packClientBack)));
        assertThat(client.getPackClients()).containsOnly(packClientBack);
        assertThat(packClientBack.getNomClient()).isEqualTo(client);

        client.setPackClients(new HashSet<>());
        assertThat(client.getPackClients()).doesNotContain(packClientBack);
        assertThat(packClientBack.getNomClient()).isNull();
    }

    @Test
    void reservationTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Reservation reservationBack = getReservationRandomSampleGenerator();

        client.addReservation(reservationBack);
        assertThat(client.getReservations()).containsOnly(reservationBack);
        assertThat(reservationBack.getNomClient()).isEqualTo(client);

        client.removeReservation(reservationBack);
        assertThat(client.getReservations()).doesNotContain(reservationBack);
        assertThat(reservationBack.getNomClient()).isNull();

        client.reservations(new HashSet<>(Set.of(reservationBack)));
        assertThat(client.getReservations()).containsOnly(reservationBack);
        assertThat(reservationBack.getNomClient()).isEqualTo(client);

        client.setReservations(new HashSet<>());
        assertThat(client.getReservations()).doesNotContain(reservationBack);
        assertThat(reservationBack.getNomClient()).isNull();
    }
}
