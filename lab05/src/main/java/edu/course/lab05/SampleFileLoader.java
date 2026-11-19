package edu.course.lab05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SampleFileLoader {

    public LoadResult load(Path path) {
        LoadResult result = new LoadResult();
        List<String> lines;

        // 1. Чтение файла (ошибка источника)
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Сохраняем причину (cause)
            throw new DataLoadingException("Cannot read " + path, e);
        }

        if (lines.isEmpty()) {
            throw new DataLoadingException("Файл пустой, нет заголовка", null);
        }

        // 2. Проверка заголовка
        String header = lines.get(0).trim();
        if (!header.equals("id;label;source;featureCount;status")) {
            throw new DataLoadingException("Неверный заголовок: " + header, null);
        }

        // 3. Обработка строк
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue; // Пропускаем пустые строки

            int lineNumber = i + 1; // Номера строк для людей (с 1)
            String[] parts = line.split(";");

            if (parts.length != 5) {
                result.addError(new ValidationError(lineNumber, "row", "Ожидалось 5 полей, найдено " + parts.length));
                continue;
            }

            try {
                String id = parts[0].trim();
                String label = parts[1].trim();
                String source = parts[2].trim();
                
                // Обработка числа
                int featureCount;
                try {
                    featureCount = Integer.parseInt(parts[3].trim());
                } catch (NumberFormatException e) {
                    result.addError(new ValidationError(lineNumber, "featureCount", "Неверное число: " + parts[3]));
                    continue;
                }

                // Обработка enum
                SampleStatus status;
                try {
                    status = SampleStatus.valueOf(parts[4].trim());
                } catch (IllegalArgumentException e) {
                    result.addError(new ValidationError(lineNumber, "status", "Неизвестный статус: " + parts[4]));
                    continue;
                }

                // Создание объекта (валидация в конструкторе)
                DataSample sample = new DataSample(id, label, source, featureCount, status);
                result.addSample(sample);

            } catch (IllegalArgumentException e) {
                // Ловим ошибки из конструктора DataSample (пустые поля)
                result.addError(new ValidationError(lineNumber, "general", e.getMessage()));
            }
        }

        return result;
    }
}