package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MeanCenteringNormalizationTest {

    @Test
    void subtractsMeanFromEachValue() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();

        double[] actual = strategy.normalize(new double[]{1.0, 2.0, 3.0});

        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, actual, 1e-9);
    }

    @Test
    void doesNotChangeInputArray() {
        MeanCenteringNormalization strategy = new MeanCenteringNormalization();
        double[] input = {1.0, 2.0, 3.0};
        double[] original = input.clone();

        strategy.normalize(input);

        assertArrayEquals(original, input, 1e-9);
    }
}