package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PackClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PackClient getPackClientSample1() {
        return new PackClient().id(1L);
    }

    public static PackClient getPackClientSample2() {
        return new PackClient().id(2L);
    }

    public static PackClient getPackClientRandomSampleGenerator() {
        return new PackClient().id(longCount.incrementAndGet());
    }
}
