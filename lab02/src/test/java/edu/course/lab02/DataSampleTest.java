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
}