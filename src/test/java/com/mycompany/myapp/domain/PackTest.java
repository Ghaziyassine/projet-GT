package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.PackClientTestSamples.*;
import static com.mycompany.myapp.domain.PackTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PackTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pack.class);
        Pack pack1 = getPackSample1();
        Pack pack2 = new Pack();
        assertThat(pack1).isNotEqualTo(pack2);

        pack2.setId(pack1.getId());
        assertThat(pack1).isEqualTo(pack2);

        pack2 = getPackSample2();
        assertThat(pack1).isNotEqualTo(pack2);
    }

    @Test
    void packClientTest() throws Exception {
        Pack pack = getPackRandomSampleGenerator();
        PackClient packClientBack = getPackClientRandomSampleGenerator();

        pack.addPackClient(packClientBack);
        assertThat(pack.getPackClients()).containsOnly(packClientBack);
        assertThat(packClientBack.getNomPack()).isEqualTo(pack);

        pack.removePackClient(packClientBack);
        assertThat(pack.getPackClients()).doesNotContain(packClientBack);
        assertThat(packClientBack.getNomPack()).isNull();

        pack.packClients(new HashSet<>(Set.of(packClientBack)));
        assertThat(pack.getPackClients()).containsOnly(packClientBack);
        assertThat(packClientBack.getNomPack()).isEqualTo(pack);

        pack.setPackClients(new HashSet<>());
        assertThat(pack.getPackClients()).doesNotContain(packClientBack);
        assertThat(packClientBack.getNomPack()).isNull();
    }
}
