package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {

    @Test
    void noDiscountReturnsOriginalPrice() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());

        assertEquals(100.0, calculator.calculate(100.0), 1e-9);
    }

    @Test
    void percentDiscountAppliesTwentyPercent() {
        PriceCalculator calculator = new PriceCalculator(new PercentDiscount(20));

        assertEquals(80.0, calculator.calculate(100.0), 1e-9);
    }

    @Test
    void replacesDiscountPolicyWithoutChangingCalculator() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(100.0, calculator.calculate(100.0), 1e-9);

        calculator = new PriceCalculator(new PercentDiscount(20));
        assertEquals(80.0, calculator.calculate(100.0), 1e-9);
    }

    @Test
    void rejectsNegativePrice() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());

        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculate(-1.0));
    }

    @Test
    void rejectsPercentOutsideRange() {
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscount(-1));
        assertThrows(IllegalArgumentException.class,
                () -> new PercentDiscount(101));
    }
}