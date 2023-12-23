package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClubTestSamples.*;
import static com.mycompany.myapp.domain.TerrainTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Club.class);
        Club club1 = getClubSample1();
        Club club2 = new Club();
        assertThat(club1).isNotEqualTo(club2);

        club2.setId(club1.getId());
        assertThat(club1).isEqualTo(club2);

        club2 = getClubSample2();
        assertThat(club1).isNotEqualTo(club2);
    }

    @Test
    void terrainTest() throws Exception {
        Club club = getClubRandomSampleGenerator();
        Terrain terrainBack = getTerrainRandomSampleGenerator();

        club.addTerrain(terrainBack);
        assertThat(club.getTerrains()).containsOnly(terrainBack);
        assertThat(terrainBack.getNomClub()).isEqualTo(club);

        club.removeTerrain(terrainBack);
        assertThat(club.getTerrains()).doesNotContain(terrainBack);
        assertThat(terrainBack.getNomClub()).isNull();

        club.terrains(new HashSet<>(Set.of(terrainBack)));
        assertThat(club.getTerrains()).containsOnly(terrainBack);
        assertThat(terrainBack.getNomClub()).isEqualTo(club);

        club.setTerrains(new HashSet<>());
        assertThat(club.getTerrains()).doesNotContain(terrainBack);
        assertThat(terrainBack.getNomClub()).isNull();
    }
}
