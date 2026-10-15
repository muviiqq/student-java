package edu.course.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataSampleTest {

    @Test
    void createValidDataSample() {
        double[] features = {1.0, 2.0, 3.0};
        DataSample sample = new DataSample("id1", "Cat", features);
        assertEquals("id1", sample.getId());
        assertEquals("Cat", sample.getLabel());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertFalse(sample.isReady());
    }

    @Test
    void changeStatusAndCheckReadiness() {
        DataSample sample = new DataSample("id1", "Cat", new double[]{1.0});
        sample.changeStatus(SampleStatus.READY);
        assertTrue(sample.isReady());
    }

    @Test
    void nullOrEmptyIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample(null, "Cat", new double[]{1.0}));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("", "Cat", new double[]{1.0}));
    }

    @Test
    void nullLabelThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id1", null, new double[]{1.0}));
    }

    @Test
    void nullFeaturesThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample("id1", "Cat", null));
    }

    @Test
    void averageFeaturesCalculatedCorrectly() {
        DataSample sample = new DataSample("id1", "Cat", new double[]{1.0, 2.0, 3.0});
        assertEquals(2.0, sample.averageFeatures(), 0.0001);
    }

    @Test
    void featuresArrayIsDefensivelyCopied() {
        double[] features = {1.0, 2.0};
        DataSample sample = new DataSample("id1", "Cat", features);
        features[0] = 99.0; // меняем оригинал
        assertEquals(1.0, sample.getFeatures()[0], 0.0001); // внутри объекта не изменилось
    }
        @Test
    void normalizedFeaturesCalculatedCorrectly() {
        DataSample sample = new DataSample("id1", "Cat",
                new double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        double[] normalized = sample.normalizedFeatures();

        assertEquals(0.0, normalized[0], 0.0001);   // min -> 0
        assertEquals(0.25, normalized[1], 0.0001);
        assertEquals(0.5, normalized[2], 0.0001);
        assertEquals(0.75, normalized[3], 0.0001);
        assertEquals(1.0, normalized[4], 0.0001);   // max -> 1
    }

    @Test
    void normalizedFeaturesDoesNotModifyOriginalObject() {
        double[] original = {1.0, 2.0, 3.0};
        DataSample sample = new DataSample("id1", "Cat", original);

        double[] normalized = sample.normalizedFeatures();

        // Исходный массив внутри объекта не изменился
        assertArrayEquals(new double[]{1.0, 2.0, 3.0}, sample.getFeatures(), 0.0001);
        // Возвращённый массив — это другой объект
        assertNotSame(normalized, sample.getFeatures());
    }

    @Test
    void normalizedFeaturesWithEqualValuesReturnsZeros() {
        DataSample sample = new DataSample("id1", "Cat",
                new double[]{5.0, 5.0, 5.0});
        double[] normalized = sample.normalizedFeatures();
        assertArrayEquals(new double[]{0.0, 0.0, 0.0}, normalized, 0.0001);
    }

    @Test
    void normalizedFeaturesWithNegativeValues() {
        DataSample sample = new DataSample("id1", "Cat",
                new double[]{-10.0, 0.0, 10.0});
        double[] normalized = sample.normalizedFeatures();
        assertEquals(0.0,  normalized[0], 0.0001);
        assertEquals(0.5,  normalized[1], 0.0001);
        assertEquals(1.0,  normalized[2], 0.0001);
    }
}