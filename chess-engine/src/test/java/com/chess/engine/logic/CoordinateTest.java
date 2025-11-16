package com.chess.engine.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    @DisplayName("Проверка конструктора с char и int")
    void testCharIntConstructor() {
        Coordinate coord = new Coordinate('e', 4);
        assertEquals('e', coord.getFile());
        assertEquals(4, coord.getRank());
    }

    @Test
    @DisplayName("Проверка конструктора копирования")
    void testCopyConstructor() {
        Coordinate original = new Coordinate('d', 5);
        Coordinate copy = new Coordinate(original);
        assertEquals(original.getFile(), copy.getFile());
        assertEquals(original.getRank(), copy.getRank());
        assertNotSame(original, copy);
    }

    @Test
    @DisplayName("Проверка строкового конструктора с корректной строкой")
    void testStringConstructorValid() {
        Coordinate coord = new Coordinate("h8");
        assertEquals('h', coord.getFile());
        assertEquals(8, coord.getRank());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с некорректной строкой")
    void testStringConstructorInvalid() {
        Coordinate coord = new Coordinate("invalid");
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
    }

    @Test
    @DisplayName("Проверка строкового конструктора с трехзначной строкой")
    void testStringConstructorThreeChars() {
        Coordinate coord = new Coordinate("j10");
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
    }

    @Test
    @DisplayName("Проверка пустого конструктора")
    void testEmptyConstructor() {
        Coordinate coord = new Coordinate();
        assertEquals(0, coord.getFile());
        assertEquals(0, coord.getRank());
    }

    @Test
    @DisplayName("Проверка метода getFile")
    void testGetFile() {
        Coordinate coord = new Coordinate('a', 1);
        assertEquals('a', coord.getFile());
    }

    @Test
    @DisplayName("Проверка метода getRank")
    void testGetRank() {
        Coordinate coord = new Coordinate('a', 1);
        assertEquals(1, coord.getRank());
    }

    @Test
    @DisplayName("Проверка inBoard с валидными координатами")
    void testInBoardValid() {
        assertTrue(Coordinate.inBoard(new Coordinate('a', 1)));
        assertTrue(Coordinate.inBoard(new Coordinate('j', 10)));
        assertTrue(Coordinate.inBoard(new Coordinate('e', 5)));
    }

    @Test
    @DisplayName("Проверка inBoard с невалидными координатами")
    void testInBoardInvalid() {
        assertFalse(Coordinate.inBoard(new Coordinate('a', 0)));
        assertFalse(Coordinate.inBoard(new Coordinate('k', 5)));
        assertFalse(Coordinate.inBoard(new Coordinate('a', 11)));
        assertFalse(Coordinate.inBoard(new Coordinate('@', 5)));
    }

    @Test
    @DisplayName("Проверка граничных значений для inBoard")
    void testInBoardBoundaries() {
        assertTrue(Coordinate.inBoard(new Coordinate('a', 1)));
        assertTrue(Coordinate.inBoard(new Coordinate('j', 10)));
        assertFalse(Coordinate.inBoard(new Coordinate('a', 0)));
        assertFalse(Coordinate.inBoard(new Coordinate('j', 11)));
        assertFalse(Coordinate.inBoard(new Coordinate('`', 1)));
        assertFalse(Coordinate.inBoard(new Coordinate('k', 1)));
    }

    @Test
    @DisplayName("Проверка метода toString")
    void testToString() {
        assertEquals("a1", new Coordinate('a', 1).toString());
        assertEquals("j10", new Coordinate('j', 10).toString());
        assertEquals("e4", new Coordinate('e', 4).toString());
    }

    @Test
    @DisplayName("Проверка равенства координат")
    void testEquals() {
        Coordinate coord1 = new Coordinate('e', 4);
        Coordinate coord2 = new Coordinate('e', 4);
        Coordinate coord3 = new Coordinate('d', 4);
        Coordinate coord4 = new Coordinate('e', 5);

        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
        assertNotEquals(coord1, coord4);
        assertEquals(coord1, coord1);
        assertNotEquals(coord1, null);
        assertNotEquals(coord1, "e4");
    }

    @Test
    @DisplayName("Проверка хэш-кода")
    void testHashCode() {
        Coordinate coord1 = new Coordinate('e', 4);
        Coordinate coord2 = new Coordinate('e', 4);
        Coordinate coord3 = new Coordinate('d', 4);

        assertEquals(coord1.hashCode(), coord2.hashCode());
        assertNotEquals(coord1.hashCode(), coord3.hashCode());
    }

    @Test
    @DisplayName("Проверка пустой координаты")
    void testEmptyCoordinate() {
        assertEquals(0, Coordinate.emptyCoordinate.getFile());
        assertEquals(0, Coordinate.emptyCoordinate.getRank());
        assertFalse(Coordinate.inBoard(Coordinate.emptyCoordinate));
    }

    @Test
    @DisplayName("Проверка автоматического приведения к нижнему регистру")
    void testLowerCaseConversion() {
        Coordinate upperCase = new Coordinate('E', 4);
        assertEquals('e', upperCase.getFile());
        assertEquals(4, upperCase.getRank());
    }

    @Test
    @DisplayName("Проверка всех возможных координат доски")
    void testAllBoardCoordinates() {
        for (char file = 'a'; file <= 'j'; file++) {
            for (int rank = 1; rank <= 10; rank++) {
                Coordinate coord = new Coordinate(file, rank);
                assertTrue(Coordinate.inBoard(coord));
            }
        }
    }

    @Test
    @DisplayName("Проверка различных форматов строкового конструктора")
    void testVariousStringFormats() {
        assertEquals('a', new Coordinate("a1").getFile());
        assertEquals(1, new Coordinate("a1").getRank());

        assertEquals('e', new Coordinate("E5").getFile());
        assertEquals(5, new Coordinate("E5").getRank());

        assertEquals('j', new Coordinate("j9").getFile());
        assertEquals(9, new Coordinate("j9").getRank());
    }

    @Test
    @DisplayName("Проверка координаты j10 через конструктор char-int")
    void testJ10Coordinate() {
        Coordinate j10 = new Coordinate('j', 10);
        assertEquals('j', j10.getFile());
        assertEquals(10, j10.getRank());
        assertTrue(Coordinate.inBoard(j10));
        assertEquals("j10", j10.toString());
    }
}