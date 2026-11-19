package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SampleFileLoader {

    public LoadResult load(Path path) {
        LoadResult result = new LoadResult();
        

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            
            // 1. Чтение заголовка
            String header = reader.readLine();
            if (header == null) {
                throw new DataLoadingException("Файл пустой, нет заголовка", null);
            }
            
            
            header = header.trim();
            if (!header.equals("id;label;source;featureCount;status")) {
                throw new DataLoadingException("Неверный заголовок: " + header, null);
            }

            
            String line;
            int lineNumber = 1; 
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) continue;

                String[] parts = trimmedLine.split(";");

                if (parts.length != 5) {
                    result.addError(new ValidationError(lineNumber, "row", "Ожидалось 5 полей, найдено " + parts.length));
                    continue;
                }

                try {
                    String id = parts[0].trim();
                    String label = parts[1].trim();
                    String source = parts[2].trim();
                    
                    int featureCount;
                    try {
                        featureCount = Integer.parseInt(parts[3].trim());
                    } catch (NumberFormatException e) {
                        result.addError(new ValidationError(lineNumber, "featureCount", "Неверное число: " + parts[3]));
                        continue;
                    }

                    SampleStatus status;
                    try {
                        status = SampleStatus.valueOf(parts[4].trim());
                    } catch (IllegalArgumentException e) {
                        result.addError(new ValidationError(lineNumber, "status", "Неизвестный статус: " + parts[4]));
                        continue;
                    }

                    DataSample sample = new DataSample(id, label, source, featureCount, status);
                    result.addSample(sample);

                } catch (IllegalArgumentException e) {
                    result.addError(new ValidationError(lineNumber, "general", e.getMessage()));
                }
            }

        } catch (IOException e) {
            
            throw new DataLoadingException("Cannot read " + path, e);
        }

        return result;
    }
}