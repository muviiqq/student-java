package edu.course.lab03;

public class FeatureProcessor {
    private final NormalizationStrategy normalizationStrategy;

    public FeatureProcessor(NormalizationStrategy normalizationStrategy) {
        this.normalizationStrategy = normalizationStrategy;
    }

    public double[] process(double[] values) {
        return normalizationStrategy.normalize(values);
    }
}