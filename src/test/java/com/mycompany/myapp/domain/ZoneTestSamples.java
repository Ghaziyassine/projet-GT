package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZoneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zone getZoneSample1() {
        return new Zone().id(1L).nomZone("nomZone1");
    }

    public static Zone getZoneSample2() {
        return new Zone().id(2L).nomZone("nomZone2");
    }

    public static Zone getZoneRandomSampleGenerator() {
        return new Zone().id(longCount.incrementAndGet()).nomZone(UUID.randomUUID().toString());
    }
}
