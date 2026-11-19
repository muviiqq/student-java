package edu.course.lab05;

import java.util.ArrayList;
import java.util.List;

public final class LoadResult {
    private final List<DataSample> samples = new ArrayList<>();
    private final List<ValidationError> errors = new ArrayList<>();

    public void addSample(DataSample sample) { samples.add(sample); }
    public void addError(ValidationError error) { errors.add(error); }

    public List<DataSample> getSamples() { return samples; }
    public List<ValidationError> getErrors() { return errors; }
}