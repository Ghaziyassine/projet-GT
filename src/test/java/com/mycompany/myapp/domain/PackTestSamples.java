package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PackTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pack getPackSample1() {
        return new Pack().id(1L).nomPack("nomPack1").tarif("tarif1").nbrDeMatches(1L);
    }

    public static Pack getPackSample2() {
        return new Pack().id(2L).nomPack("nomPack2").tarif("tarif2").nbrDeMatches(2L);
    }

    public static Pack getPackRandomSampleGenerator() {
        return new Pack()
            .id(longCount.incrementAndGet())
            .nomPack(UUID.randomUUID().toString())
            .tarif(UUID.randomUUID().toString())
            .nbrDeMatches(longCount.incrementAndGet());
    }
}
