package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;

public class SampleRegistry {
    // Композиция: реестр содержит HashMap
    private final Map<SampleId, DataSample> storage = new HashMap<>();

    // Добавить или заменить
    public void put(SampleId id, DataSample sample) {
        storage.put(id, sample);
    }

    // Получить
    public DataSample get(SampleId id) {
        return storage.get(id);
    }

    // Удалить
    public void remove(SampleId id) {
        storage.remove(id);
    }

    // Проверить наличие
    public boolean contains(SampleId id) {
        return storage.containsKey(id);
    }

    // Узнать размер
    public int size() {
        return storage.size();
    }
}