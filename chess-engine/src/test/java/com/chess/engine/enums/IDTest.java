package com.chess.engine.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class IDTest {


    @ParameterizedTest
    @EnumSource(ID.class)
    @DisplayName("Проверка что все элементы перечисления не null")
    void testNoNullValues(ID pieceType) {
        assertNotNull(pieceType);
        assertNotNull(pieceType.toString());
        assertNotNull(pieceType.toFullString());
    }

    @ParameterizedTest
    @CsvSource({
            "KING, K, King",
            "QUEEN, Q, Queen",
            "ROOK, R, Rook",
            "BISHOP, B, Bishop",
            "KNIGHT, N, Knight",
            "PAWN, '', Pawn",
            "VIZAR, V, Vizar",
            "WARCAR, W, WarCar",
            "GIRAFFE, G, Giraffe"
    })
    @DisplayName("Проверка строковых представлений фигур")
    void testStringRepresentations(ID pieceType, String expectedShort, String expectedFull) {
        assertEquals(expectedShort, pieceType.toString());
        assertEquals(expectedFull, pieceType.toFullString());
    }

    @Test
    @DisplayName("Проверка специального случая - пустая строка у пешки")
    void testPawnEmptyString() {
        assertEquals("", ID.PAWN.toString());
        assertEquals("Pawn", ID.PAWN.toFullString());
    }

    @Test
    @DisplayName("Проверка уникальности сокращенных обозначений")
    void testUniqueShortNotations() {
        // Собираем все сокращенные обозначения
        String[] shortNotations = new String[ID.values().length];
        for (int i = 0; i < ID.values().length; i++) {
            shortNotations[i] = ID.values()[i].toString();
        }

        // Проверяем уникальность (игнорируя пешку с пустой строкой)
        for (int i = 0; i < shortNotations.length; i++) {
            for (int j = i + 1; j < shortNotations.length; j++) {
                if (!shortNotations[i].isEmpty() && !shortNotations[j].isEmpty()) {
                    assertNotEquals(shortNotations[i], shortNotations[j],
                            "Сокращенные обозначения должны быть уникальными для " +
                                    ID.values()[i] + " и " + ID.values()[j]);
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка получения значений по имени")
    void testValueOf() {
        assertEquals(ID.KING, ID.valueOf("KING"));
        assertEquals(ID.QUEEN, ID.valueOf("QUEEN"));
        assertEquals(ID.ROOK, ID.valueOf("ROOK"));
        assertEquals(ID.BISHOP, ID.valueOf("BISHOP"));
        assertEquals(ID.KNIGHT, ID.valueOf("KNIGHT"));
        assertEquals(ID.PAWN, ID.valueOf("PAWN"));
        assertEquals(ID.VIZAR, ID.valueOf("VIZAR"));
        assertEquals(ID.WARCAR, ID.valueOf("WARCAR"));
        assertEquals(ID.GIRAFFE, ID.valueOf("GIRAFFE"));
    }

    @Test
    @DisplayName("Проверка специальных фигур")
    void testSpecialPieces() {
        // Проверяем визиря
        assertEquals("V", ID.VIZAR.toString());
        assertEquals("Vizar", ID.VIZAR.toFullString());

        // Проверяем боевую машину
        assertEquals("W", ID.WARCAR.toString());
        assertEquals("WarCar", ID.WARCAR.toFullString());

        // Проверяем жирафа
        assertEquals("G", ID.GIRAFFE.toString());
        assertEquals("Giraffe", ID.GIRAFFE.toFullString());
    }

    @ParameterizedTest
    @EnumSource(ID.class)
    @DisplayName("Проверка что полные названия не пустые и содержат только буквы")
    void testFullStringFormat(ID pieceType) {
        String fullString = pieceType.toFullString();
        assertFalse(fullString.isEmpty(), "Полное название не должно быть пустым для " + pieceType);
        assertTrue(fullString.matches("[a-zA-Z]+"),
                "Полное название должно содержать только буквы для " + pieceType + ": " + fullString);
    }

    @Test
    @DisplayName("Проверка порядка значений перечисления")
    void testEnumOrder() {
        ID[] expectedOrder = {ID.KING, ID.QUEEN, ID.ROOK, ID.BISHOP,
                ID.KNIGHT, ID.PAWN, ID.VIZAR, ID.WARCAR, ID.GIRAFFE};
        assertArrayEquals(expectedOrder, ID.values(), "Порядок значений перечисления должен соответствовать объявлению");
    }
}