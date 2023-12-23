package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.TerrainTestSamples.*;
import static com.mycompany.myapp.domain.VilleTestSamples.*;
import static com.mycompany.myapp.domain.ZoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ZoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zone.class);
        Zone zone1 = getZoneSample1();
        Zone zone2 = new Zone();
        assertThat(zone1).isNotEqualTo(zone2);

        zone2.setId(zone1.getId());
        assertThat(zone1).isEqualTo(zone2);

        zone2 = getZoneSample2();
        assertThat(zone1).isNotEqualTo(zone2);
    }

    @Test
    void terrainTest() throws Exception {
        Zone zone = getZoneRandomSampleGenerator();
        Terrain terrainBack = getTerrainRandomSampleGenerator();

        zone.addTerrain(terrainBack);
        assertThat(zone.getTerrains()).containsOnly(terrainBack);
        assertThat(terrainBack.getNomZone()).isEqualTo(zone);

        zone.removeTerrain(terrainBack);
        assertThat(zone.getTerrains()).doesNotContain(terrainBack);
        assertThat(terrainBack.getNomZone()).isNull();

        zone.terrains(new HashSet<>(Set.of(terrainBack)));
        assertThat(zone.getTerrains()).containsOnly(terrainBack);
        assertThat(terrainBack.getNomZone()).isEqualTo(zone);

        zone.setTerrains(new HashSet<>());
        assertThat(zone.getTerrains()).doesNotContain(terrainBack);
        assertThat(terrainBack.getNomZone()).isNull();
    }

    @Test
    void nomVilleTest() throws Exception {
        Zone zone = getZoneRandomSampleGenerator();
        Ville villeBack = getVilleRandomSampleGenerator();

        zone.setNomVille(villeBack);
        assertThat(zone.getNomVille()).isEqualTo(villeBack);

        zone.nomVille(null);
        assertThat(zone.getNomVille()).isNull();
    }
}
