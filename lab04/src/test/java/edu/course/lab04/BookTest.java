package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void bookWorksAsKeyInHashMap() {
        Book book1 = new Book("123", "Java Basics");
        Book book2 = new Book("123", "Java Advanced"); // Другой title, тот же ISBN

        Map<Book, String> library = new HashMap<>();
        library.put(book1, "Полка 1");

        // Ищем по второму экземпляру, но находим значение первого
        assertEquals("Полка 1", library.get(book2));
    }
}