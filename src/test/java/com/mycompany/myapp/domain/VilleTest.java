package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.VilleTestSamples.*;
import static com.mycompany.myapp.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VilleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ville.class);
        Ville ville1 = getVilleSample1();
        Ville ville2 = new Ville();
        assertThat(ville1).isNotEqualTo(ville2);

        ville2.setId(ville1.getId());
        assertThat(ville1).isEqualTo(ville2);

        ville2 = getVilleSample2();
        assertThat(ville1).isNotEqualTo(ville2);
    }

    @Test
    void zoneTest() throws Exception {
        Ville ville = getVilleRandomSampleGenerator();
        Zone zoneBack = getZoneRandomSampleGenerator();

        ville.addZone(zoneBack);
        assertThat(ville.getZones()).containsOnly(zoneBack);
        assertThat(zoneBack.getNomVille()).isEqualTo(ville);

        ville.removeZone(zoneBack);
        assertThat(ville.getZones()).doesNotContain(zoneBack);
        assertThat(zoneBack.getNomVille()).isNull();

        ville.zones(new HashSet<>(Set.of(zoneBack)));
        assertThat(ville.getZones()).containsOnly(zoneBack);
        assertThat(zoneBack.getNomVille()).isEqualTo(ville);

        ville.setZones(new HashSet<>());
        assertThat(ville.getZones()).doesNotContain(zoneBack);
        assertThat(zoneBack.getNomVille()).isNull();
    }
}
