package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeatureProcessorTest {

    @Test
    void processesVectorWithMinMaxStrategy() {
        FeatureProcessor processor =
                new FeatureProcessor(new MinMaxNormalization());

        double[] actual = processor.process(new double[]{10.0, 20.0, 30.0});

        assertArrayEquals(new double[]{0.0, 0.5, 1.0}, actual, 1e-9);
    }

    @Test
    void processesVectorWithMeanCenteringStrategy() {
        FeatureProcessor processor =
                new FeatureProcessor(new MeanCenteringNormalization());

        double[] actual = processor.process(new double[]{1.0, 2.0, 3.0});

        assertArrayEquals(new double[]{-1.0, 0.0, 1.0}, actual, 1e-9);
    }

    @Test
    void replacesStrategyWithoutChangingProcessor() {
        double[] input = {1.0, 2.0, 3.0};

        FeatureProcessor processor =
                new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[]{-1.0, 0.0, 1.0},
                processor.process(input), 1e-9);

        processor = new FeatureProcessor(new MinMaxNormalization());
        assertArrayEquals(new double[]{0.0, 0.5, 1.0},
                processor.process(input), 1e-9);
    }
}