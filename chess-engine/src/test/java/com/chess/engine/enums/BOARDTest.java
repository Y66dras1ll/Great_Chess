package com.chess.engine.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BOARDTest {

    @Test
    void testFileValues() {

        assertEquals('a', BOARD.FIRST_FILE.getFileVal());
        assertEquals('j', BOARD.LAST_FILE.getFileVal());
    }

    @Test
    void testRankValues() {

        assertEquals(1, BOARD.FIRST_RANK.getRankVal());
        assertEquals(10, BOARD.LAST_RANK.getRankVal());
    }

    @Test
    void testEnumConstants() {

        assertNotNull(BOARD.valueOf("FIRST_FILE"));
        assertNotNull(BOARD.valueOf("LAST_FILE"));
        assertNotNull(BOARD.valueOf("FIRST_RANK"));
        assertNotNull(BOARD.valueOf("LAST_RANK"));
    }

    @Test
    void testFileConstructor() {
        assertEquals('a', BOARD.FIRST_FILE.getFileVal());
        assertEquals('j', BOARD.LAST_FILE.getFileVal());


        assertEquals(0, BOARD.FIRST_FILE.getRankVal());
        assertEquals(0, BOARD.LAST_FILE.getRankVal());
    }

    @Test
    void testRankConstructor() {
        assertEquals(1, BOARD.FIRST_RANK.getRankVal());
        assertEquals(10, BOARD.LAST_RANK.getRankVal());

        assertEquals('\0', BOARD.FIRST_RANK.getFileVal());
        assertEquals('\0', BOARD.LAST_RANK.getFileVal());
    }
}