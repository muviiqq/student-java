package edu.course.lab03;

import java.util.Arrays;

public class MeanCenteringNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        double[] result = Arrays.copyOf(values, values.length);
        if (result.length == 0) {
            return result;
        }

        double sum = 0.0;
        for (double value : result) {
            sum += value;
        }

        double mean = sum / result.length;

        for (int i = 0; i < result.length; i++) {
            result[i] = result[i] - mean;
        }

        return result;
    }
}