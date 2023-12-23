package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClientTestSamples.*;
import static com.mycompany.myapp.domain.PackClientTestSamples.*;
import static com.mycompany.myapp.domain.PackTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackClient.class);
        PackClient packClient1 = getPackClientSample1();
        PackClient packClient2 = new PackClient();
        assertThat(packClient1).isNotEqualTo(packClient2);

        packClient2.setId(packClient1.getId());
        assertThat(packClient1).isEqualTo(packClient2);

        packClient2 = getPackClientSample2();
        assertThat(packClient1).isNotEqualTo(packClient2);
    }

    @Test
    void nomClientTest() throws Exception {
        PackClient packClient = getPackClientRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        packClient.setNomClient(clientBack);
        assertThat(packClient.getNomClient()).isEqualTo(clientBack);

        packClient.nomClient(null);
        assertThat(packClient.getNomClient()).isNull();
    }

    @Test
    void nomPackTest() throws Exception {
        PackClient packClient = getPackClientRandomSampleGenerator();
        Pack packBack = getPackRandomSampleGenerator();

        packClient.setNomPack(packBack);
        assertThat(packClient.getNomPack()).isEqualTo(packBack);

        packClient.nomPack(null);
        assertThat(packClient.getNomPack()).isNull();
    }
}
