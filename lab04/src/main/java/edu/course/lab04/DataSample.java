package edu.course.lab04;

public class DataSample {
    private final String label;
    private SampleStatus status;

    public DataSample(String label) {
        this.label = label;
        this.status = SampleStatus.NEW;
    }

    public String getLabel() { return label; }
    public SampleStatus getStatus() { return status; }
    public void setStatus(SampleStatus status) { this.status = status; }
}