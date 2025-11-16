package com.chess.engine.notation;

import com.chess.engine.enums.COLOUR;

import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Pieces;
import com.chess.engine.pieces.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;


import java.nio.file.Path;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ChessIOTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Проверка форматирования хода ладьи")
    void testMoveStringRook() {
        HashMap<Coordinate, Piece> board = new HashMap<>();

        Coordinate rookCoord = new Coordinate('a', 1);
        Rook rook = new Rook(COLOUR.W, rookCoord);
        board.put(rookCoord, rook);

        Coordinate kingCoord = new Coordinate('e', 1);
        King king = new King(COLOUR.W, kingCoord);
        board.put(kingCoord, king);

        Coordinate blackKingCoord = new Coordinate('e', 8);
        King blackKing = new King(COLOUR.B, blackKingCoord);
        board.put(blackKingCoord, blackKing);

        Pieces pieces = new Pieces(board);
        pieces.updatePotentials();
        pieces.setPreviousPieces(pieces.getPieces());

        Coordinate moveCoord = new Coordinate('a', 4);
        String move = ChessIO.moveString(pieces, moveCoord, rook);

        assertEquals("Ra4", move);
    }

    @Test
    @DisplayName("Проверка форматирования хода со взятием")
    void testMoveStringCapture() {
        HashMap<Coordinate, Piece> board = new HashMap<>();

        Coordinate pawnCoord = new Coordinate('e', 2);
        Pawn pawn = new Pawn(COLOUR.W, pawnCoord);
        board.put(pawnCoord, pawn);

        Coordinate kingCoord = new Coordinate('e', 1);
        King king = new King(COLOUR.W, kingCoord);
        board.put(kingCoord, king);

        Coordinate blackKingCoord = new Coordinate('e', 8);
        King blackKing = new King(COLOUR.B, blackKingCoord);
        board.put(blackKingCoord, blackKing);

        Coordinate blackPawnCoord = new Coordinate('d', 3);
        Pawn blackPawn = new Pawn(COLOUR.B, blackPawnCoord);
        board.put(blackPawnCoord, blackPawn);

        Pieces pieces = new Pieces(board);
        pieces.updatePotentials();
        pieces.setPreviousPieces(pieces.getPieces());

        pieces.setIsCapture(true);
        Coordinate moveCoord = new Coordinate('d', 3);
        String move = ChessIO.moveString(pieces, moveCoord, pawn);

        assertTrue(move.contains("x"));
        assertTrue(move.contains("d3"));
    }




    @Test
    @DisplayName("Проверка сохранения игры в файл")
    void testSaveGame() {
        String gameData = "1. e4 e5 2. Nf3 Nc6";
        Path testFile = tempDir.resolve("test_game.txt");

        boolean result = ChessIO.saveGame(gameData, testFile);

        assertTrue(result);
        assertTrue(testFile.toFile().exists());
    }

    @Test
    @DisplayName("Проверка сохранения игры в существующий файл")
    void testSaveGameExistingFile() {
        String gameData = "1. e4 e5 2. Nf3 Nc6";
        Path testFile = tempDir.resolve("existing_game.txt");

        try {
            testFile.toFile().createNewFile();
        } catch (Exception e) {
            fail("Не удалось создать тестовый файл");
        }

        boolean result = ChessIO.saveGame(gameData, testFile);

        assertFalse(result);
    }

    @Test
    @DisplayName("Проверка обработки null параметров при сохранении")
    void testSaveGameNullParameters() {
        Path testFile = tempDir.resolve("test.txt");

        assertThrows(NullPointerException.class, () -> ChessIO.saveGame(null, testFile));
        assertThrows(NullPointerException.class, () -> ChessIO.saveGame("game", null));
    }

    @Test
    @DisplayName("Проверка форматирования хода коня")
    void testMoveStringKnight() {
        HashMap<Coordinate, Piece> board = new HashMap<>();

        Coordinate knightCoord = new Coordinate('g', 1);
        Knight knight = new Knight(COLOUR.W, knightCoord);
        board.put(knightCoord, knight);

        Coordinate kingCoord = new Coordinate('e', 1);
        King king = new King(COLOUR.W, kingCoord);
        board.put(kingCoord, king);

        Coordinate blackKingCoord = new Coordinate('e', 8);
        King blackKing = new King(COLOUR.B, blackKingCoord);
        board.put(blackKingCoord, blackKing);

        Pieces pieces = new Pieces(board);
        pieces.updatePotentials();
        pieces.setPreviousPieces(pieces.getPieces());

        Coordinate moveCoord = new Coordinate('f', 3);
        String move = ChessIO.moveString(pieces, moveCoord, knight);

        assertEquals("Nf3", move);
    }

    @Test
    @DisplayName("Проверка форматирования хода слона")
    void testMoveStringBishop() {
        HashMap<Coordinate, Piece> board = new HashMap<>();

        Coordinate bishopCoord = new Coordinate('f', 1);
        Bishop bishop = new Bishop(COLOUR.W, bishopCoord);
        board.put(bishopCoord, bishop);

        Coordinate kingCoord = new Coordinate('e', 1);
        King king = new King(COLOUR.W, kingCoord);
        board.put(kingCoord, king);

        Coordinate blackKingCoord = new Coordinate('e', 8);
        King blackKing = new King(COLOUR.B, blackKingCoord);
        board.put(blackKingCoord, blackKing);

        Pieces pieces = new Pieces(board);
        pieces.updatePotentials();
        pieces.setPreviousPieces(pieces.getPieces());

        Coordinate moveCoord = new Coordinate('c', 4);
        String move = ChessIO.moveString(pieces, moveCoord, bishop);

        assertEquals("Bc4", move);
    }

    @Test
    @DisplayName("Проверка форматирования хода ферзя")
    void testMoveStringQueen() {
        HashMap<Coordinate, Piece> board = new HashMap<>();

        Coordinate queenCoord = new Coordinate('d', 1);
        Queen queen = new Queen(COLOUR.W, queenCoord);
        board.put(queenCoord, queen);

        Coordinate kingCoord = new Coordinate('e', 1);
        King king = new King(COLOUR.W, kingCoord);
        board.put(kingCoord, king);

        Coordinate blackKingCoord = new Coordinate('e', 8);
        King blackKing = new King(COLOUR.B, blackKingCoord);
        board.put(blackKingCoord, blackKing);

        Pieces pieces = new Pieces(board);
        pieces.updatePotentials();
        pieces.setPreviousPieces(pieces.getPieces());

        Coordinate moveCoord = new Coordinate('d', 4);
        String move = ChessIO.moveString(pieces, moveCoord, queen);

        assertEquals("Qd4", move);
    }

}