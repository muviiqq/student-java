package edu.course.lab05;

public final class DataSample {
    private final String id;
    private final String label;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public DataSample(String id, String label, String source, int featureCount, SampleStatus status) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id пустой");
        if (label == null || label.trim().isEmpty()) throw new IllegalArgumentException("label пустой");
        if (source == null || source.trim().isEmpty()) throw new IllegalArgumentException("source пустой");
        if (featureCount <= 0) throw new IllegalArgumentException("featureCount должен быть > 0");
        if (status == null) throw new IllegalArgumentException("status null");
        
        this.id = id;
        this.label = label;
        this.source = source;
        this.featureCount = featureCount;
        this.status = status;
    }

    // Геттеры
    public String getId() { return id; }
    public String getLabel() { return label; }
    public String getSource() { return source; }
    public int getFeatureCount() { return featureCount; }
    public SampleStatus getStatus() { return status; }
}