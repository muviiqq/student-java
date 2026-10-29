package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinMaxNormalizationTest {

    @Test
    void normalizesValuesToZeroOneRange() {
        MinMaxNormalization strategy = new MinMaxNormalization();

        double[] actual = strategy.normalize(new double[]{10.0, 20.0, 30.0});

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, actual, 1e-9);
    }

    @Test
    void doesNotChangeInputArray() {
        MinMaxNormalization strategy = new MinMaxNormalization();
        double[] input = {10.0, 20.0, 30.0};
        double[] original = input.clone();

        strategy.normalize(input);

        assertArrayEquals(original, input, 1e-9);
    }

    @Test
    void returnsZerosForConstantArray() {
        MinMaxNormalization strategy = new MinMaxNormalization();

        double[] actual = strategy.normalize(new double[]{5.0, 5.0, 5.0});

        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, actual, 1e-9);
    }
}