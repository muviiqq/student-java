package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SampleFileLoaderTest {

    @TempDir
    Path tempDir;

    private Path createFile(String... lines) throws IOException {
        Path file = tempDir.resolve("data.csv");
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }

    private static final String HEADER = "id;label;source;featureCount;status";

    @Test
    void loadCorrectFile() throws IOException {
        Path file = createFile(
                HEADER,
                "S-101;cat;camera-1;128;READY"
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(1, result.getSamples().size());
        assertEquals(0, result.getErrors().size());
        assertEquals("S-101", result.getSamples().get(0).getId());
    }

    @Test
    void loadUnicodeText() throws IOException {
        Path file = createFile(
                HEADER,
                "S-102;кот;камера-1;100;NEW"
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(1, result.getSamples().size());
        assertEquals("кот", result.getSamples().get(0).getLabel());
    }

    @Test
    void emptyFieldReturnsError() throws IOException {
        Path file = createFile(
                HEADER,
                "S-101;;camera-1;128;READY" // пустой label
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertTrue(result.getErrors().get(0).getMessage().contains("label пустой"));
    }

    @Test
    void wrongNumberReturnsError() throws IOException {
        Path file = createFile(
                HEADER,
                "S-101;cat;camera-1;abc;READY" // featureCount - не число
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("featureCount", result.getErrors().get(0).getField());
    }

    @Test
    void unknownStatusReturnsError() throws IOException {
        Path file = createFile(
                HEADER,
                "S-101;cat;camera-1;128;UNKNOWN" // Неизвестный статус
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void wrongColumnCountReturnsError() throws IOException {
        Path file = createFile(
                HEADER,
                "S-101;cat;camera-1;128" // Всего 4 поля вместо 5
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("row", result.getErrors().get(0).getField());
    }

    @Test
    void mixOfCorrectAndIncorrectLines() throws IOException {
        Path file = createFile(
                HEADER,
                "S-101;cat;camera-1;128;READY", // OK
                "S-102;dog;camera-1;bad;READY", // Ошибка числа
                "S-103;bird;camera-2;50;NEW"    // OK
        );
        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(2, result.getSamples().size()); // Корректные сохранены
        assertEquals(1, result.getErrors().size());  // Ошибка записана
    }

    @Test
    void missingFileThrowsDataLoadingException() {
        Path missingFile = tempDir.resolve("does_not_exist.csv");

        DataLoadingException exception = assertThrows(
                DataLoadingException.class,
                () -> new SampleFileLoader().load(missingFile)
        );

        // Проверяем, что причина (cause) сохранена
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof IOException);
    }
}