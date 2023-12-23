package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PhotoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Photo getPhotoSample1() {
        return new Photo().id(1L);
    }

    public static Photo getPhotoSample2() {
        return new Photo().id(2L);
    }

    public static Photo getPhotoRandomSampleGenerator() {
        return new Photo().id(longCount.incrementAndGet());
    }
}
