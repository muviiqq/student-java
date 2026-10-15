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
}