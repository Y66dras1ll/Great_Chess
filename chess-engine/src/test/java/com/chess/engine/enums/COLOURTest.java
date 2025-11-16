package com.chess.engine.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class COLOURTest {

    @Test
    @DisplayName("Проверка строкового представления цветов")
    void testToString() {
        assertEquals("Чёрные", COLOUR.B.toString());
        assertEquals("Белые", COLOUR.W.toString());
    }

    @Test
    @DisplayName("Проверка сокращенного строкового представления")
    void testToSmallString() {
        assertEquals("b", COLOUR.B.toSmallString());
        assertEquals("w", COLOUR.W.toSmallString());
    }

    @Test
    @DisplayName("Проверка метода not для черного цвета")
    void testNotForBlack() {
        COLOUR result = COLOUR.not(COLOUR.B);
        assertEquals(COLOUR.W, result, "Противоположный цвет для BLACK должен быть WHITE");
    }

    @Test
    @DisplayName("Проверка метода not для белого цвета")
    void testNotForWhite() {
        COLOUR result = COLOUR.not(COLOUR.W);
        assertEquals(COLOUR.B, result, "Противоположный цвет для WHITE должен быть BLACK");
    }

    @Test
    @DisplayName("Проверка всех значений перечисления")
    void testEnumValues() {
        COLOUR[] values = COLOUR.values();
        assertEquals(2, values.length, "Должно быть 2 значения в перечислении");
        assertArrayEquals(new COLOUR[]{COLOUR.B, COLOUR.W}, values);
    }

    @Test
    @DisplayName("Проверка получения значения по имени")
    void testValueOf() {
        assertEquals(COLOUR.B, COLOUR.valueOf("B"));
        assertEquals(COLOUR.W, COLOUR.valueOf("W"));
    }

    @Test
    @DisplayName("Проверка симметричности метода not")
    void testNotSymmetry() {
        // Проверяем, что дважды примененный метод not возвращает исходный цвет
        COLOUR originalBlack = COLOUR.B;
        COLOUR opposite = COLOUR.not(originalBlack);
        COLOUR backToOriginal = COLOUR.not(opposite);
        assertEquals(originalBlack, backToOriginal, "not(not(color)) должен возвращать исходный цвет");

        COLOUR originalWhite = COLOUR.W;
        COLOUR oppositeWhite = COLOUR.not(originalWhite);
        COLOUR backToOriginalWhite = COLOUR.not(oppositeWhite);
        assertEquals(originalWhite, backToOriginalWhite, "not(not(color)) должен возвращать исходный цвет");
    }

    @Test
    @DisplayName("Проверка, что метод not не возвращает null")
    void testNotNull() {
        assertNotNull(COLOUR.not(COLOUR.B));
        assertNotNull(COLOUR.not(COLOUR.W));
    }

    @Test
    @DisplayName("Проверка уникальности значений перечисления")
    void testUniqueValues() {
        assertNotEquals(COLOUR.B, COLOUR.W);
        assertNotEquals(COLOUR.B.toString(), COLOUR.W.toString());
        assertNotEquals(COLOUR.B.toSmallString(), COLOUR.W.toSmallString());
    }
}