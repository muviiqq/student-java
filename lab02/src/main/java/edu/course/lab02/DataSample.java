package edu.course.lab02;

import java.util.Arrays;

public class DataSample {
    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, double[] features) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID не может быть null или пустым");
        }
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label не может быть null или пустым");
        }
        if (features == null) {
            throw new IllegalArgumentException("Массив features не может быть null");
        }
        this.id = id;
        this.label = label;
        this.features = Arrays.copyOf(features, features.length); // защитное копирование
        this.status = SampleStatus.NEW;
    }

    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус не может быть null");
        }
        this.status = newStatus;
    }

    public boolean isReady() {
        return this.status == SampleStatus.READY;
    }

    public double averageFeatures() {
        if (features.length == 0) {
            return 0.0;
        }
        double sum = 0;
        for (double f : features) {
            sum += f;
        }
        return sum / features.length;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public SampleStatus getStatus() { return status; }
    
    public double[] getFeatures() {
        return Arrays.copyOf(features, features.length); // защитное копирование
    }
        /**
     * Нормализация признаков (Min-Max scaling) без изменения исходного массива.
     * Возвращает НОВЫЙ массив, где каждое значение приведено к диапазону [0, 1]
     * по формуле: (x - min) / (max - min).
     */
    public double[] normalizedFeatures() {
        if (features.length == 0) {
            return new double[0];
        }

        // Находим минимум и максимум
        double min = features[0];
        double max = features[0];
        for (double f : features) {
            if (f < min) min = f;
            if (f > max) max = f;
        }

        // Создаём НОВЫЙ массив — исходный features не трогаем
        double[] normalized = new double[features.length];
        double range = max - min;

        // Если все значения одинаковые — делить на 0 нельзя, возвращаем нули
        if (range == 0) {
            return normalized;
        }

        for (int i = 0; i < features.length; i++) {
            normalized[i] = (features[i] - min) / range;
        }
        return normalized;
    }
}