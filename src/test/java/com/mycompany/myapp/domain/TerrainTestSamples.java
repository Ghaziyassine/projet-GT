package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TerrainTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Terrain getTerrainSample1() {
        return new Terrain()
            .id(1L)
            .nomTerrain("nomTerrain1")
            .adresse("adresse1")
            .rank(1L)
            .type("type1")
            .etat("etat1")
            .description("description1")
            .typeSal("typeSal1")
            .tarif("tarif1");
    }

    public static Terrain getTerrainSample2() {
        return new Terrain()
            .id(2L)
            .nomTerrain("nomTerrain2")
            .adresse("adresse2")
            .rank(2L)
            .type("type2")
            .etat("etat2")
            .description("description2")
            .typeSal("typeSal2")
            .tarif("tarif2");
    }

    public static Terrain getTerrainRandomSampleGenerator() {
        return new Terrain()
            .id(longCount.incrementAndGet())
            .nomTerrain(UUID.randomUUID().toString())
            .adresse(UUID.randomUUID().toString())
            .rank(longCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .etat(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .typeSal(UUID.randomUUID().toString())
            .tarif(UUID.randomUUID().toString());
    }
}
