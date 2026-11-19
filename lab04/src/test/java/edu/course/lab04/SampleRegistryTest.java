package edu.course.lab04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SampleRegistryTest {

    // Эксперимент 1: Два разных экземпляра равного ключа находят одно значение
    @Test
    void logicallyEqualKeysFindSameValue() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id1 = new SampleId("cats", 1);
        SampleId id2 = new SampleId("cats", 1); // Другой объект, но логически равен

        DataSample sample = new DataSample("Cat_1");
        registry.put(id1, sample);

        assertSame(sample, registry.get(id2));
    }

    // Эксперимент 2: Повторное добавление заменяет значение и не увеличивает размер
    @Test
    void reAddingWithEqualKeyReplacesValue() {
        SampleRegistry registry = new SampleRegistry();
        SampleId id1 = new SampleId("dogs", 2);
        SampleId id2 = new SampleId("dogs", 2);

        registry.put(id1, new DataSample("Dog_Old"));
        registry.put(id2, new DataSample("Dog_New"));

        assertEquals(1, registry.size()); // Размер остался 1
        assertEquals("Dog_New", registry.get(id1).getLabel()); // Значение заменилось
    }

    // Эксперимент 4: Сравнение с null и объектом другого типа возвращает false
    @Test
    void equalsReturnsFalseForNullAndOtherTypes() {
        SampleId id = new SampleId("test", 1);
        
        assertFalse(id.equals(null));
        assertFalse(id.equals("строка"));
        assertFalse(id.equals(new Book("123", "Title")));
    }

        // Эксперимент 3: Коллизия (одинаковый hashCode, но разные ключи)
    @Test
    void differentKeysWithSameHashCodeDoNotOverwrite() {
        // Создаем локальный тестовый класс (не наследуемся от final SampleId)
        class CollisionKey {
            String id;
            CollisionKey(String id) { this.id = id; }
            
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                return id.equals(((CollisionKey) o).id); // Разные id -> не равны
            }
            
            @Override
            public int hashCode() { 
                return 42; // Намеренно одинаковый хеш для всех!
            }
        }

        // Используем обычный HashMap, чтобы показать работу коллизии
        java.util.Map<CollisionKey, DataSample> map = new java.util.HashMap<>();
        
        CollisionKey key1 = new CollisionKey("A");
        CollisionKey key2 = new CollisionKey("B");

        map.put(key1, new DataSample("Sample_A"));
        map.put(key2, new DataSample("Sample_B"));

        // Размер 2, значения не перезаписали друг друга
        assertEquals(2, map.size());
        assertEquals("Sample_A", map.get(key1).getLabel());
        assertEquals("Sample_B", map.get(key2).getLabel());
    }

    // Эксперимент 5: Проблема изменяемого ключа (Mutable Key)
    @Test
    void mutableKeyBreaksHashMap() {
        // Создаем ИЗМЕНЯЕМЫЙ ключ (только для эксперимента!)
        class MutableKey {
            String id;
            MutableKey(String id) { this.id = id; }
            
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                return id.equals(((MutableKey) o).id);
            }
            @Override
            public int hashCode() { return id.hashCode(); }
        }

        MutableKey key = new MutableKey("original");
        java.util.Map<MutableKey, String> map = new java.util.HashMap<>();
        
        map.put(key, "Value");
        assertEquals("Value", map.get(key)); // Работает

        // МЕНЯЕМ поле ключа (в реальном коде так делать НЕЛЬЗЯ)
        key.id = "changed";

        // Теперь HashMap не может найти ключ, потому что его hashCode изменился,
        // и он лежит не в той "корзине"
        assertNull(map.get(key)); 
        assertEquals(1, map.size()); // Но значение все еще в памяти (утечка)
    }
}