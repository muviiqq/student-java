package edu.course.lab03;

import java.util.Arrays;

public class MinMaxNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Массив не может быть null");
        }

        double[] result = Arrays.copyOf(values, values.length);
        if (result.length == 0) {
            return result;
        }

        double min = result[0];
        double max = result[0];

        for (double value : result) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        if (max == min) {
            Arrays.fill(result, 0.0);
            return result;
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = (result[i] - min) / (max - min);
        }

        return result;
    }
}