package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationTerrainTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ReservationTerrain getReservationTerrainSample1() {
        return new ReservationTerrain().id(1L).heure(1);
    }

    public static ReservationTerrain getReservationTerrainSample2() {
        return new ReservationTerrain().id(2L).heure(2);
    }

    public static ReservationTerrain getReservationTerrainRandomSampleGenerator() {
        return new ReservationTerrain().id(longCount.incrementAndGet()).heure(intCount.incrementAndGet());
    }
}
