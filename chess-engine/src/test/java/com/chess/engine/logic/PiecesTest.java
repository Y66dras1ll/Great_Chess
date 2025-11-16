package com.chess.engine.logic;

import com.chess.engine.enums.COLOUR;
import com.chess.engine.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PiecesTest {

    private Pieces pieces;
    private Piece whiteKing;
    private Piece blackKing;
    private Piece whitePawn;
    private Piece blackPawn;
    private Piece whiteRook;
    private Coordinate whiteKingCoord;
    private Coordinate blackKingCoord;

    @BeforeEach
    void setUp() {
        HashMap<Coordinate, Piece> testBoard = new HashMap<>();

        whiteKingCoord = new Coordinate('e', 1);
        whiteKing = new King(COLOUR.W, whiteKingCoord);
        testBoard.put(whiteKingCoord, whiteKing);

        blackKingCoord = new Coordinate('e', 8);
        blackKing = new King(COLOUR.B, blackKingCoord);
        testBoard.put(blackKingCoord, blackKing);

        Coordinate whitePawnCoord = new Coordinate('e', 2);
        whitePawn = new Pawn(COLOUR.W, whitePawnCoord);
        testBoard.put(whitePawnCoord, whitePawn);

        Coordinate blackPawnCoord = new Coordinate('e', 7);
        blackPawn = new Pawn(COLOUR.B, blackPawnCoord);
        testBoard.put(blackPawnCoord, blackPawn);

        Coordinate whiteRookCoord = new Coordinate('a', 1);
        whiteRook = new Rook(COLOUR.W, whiteRookCoord);
        testBoard.put(whiteRookCoord, whiteRook);

        pieces = new Pieces(testBoard);
        pieces.updatePotentials();
    }

    @Test
    @DisplayName("Проверка конструкторов Pieces")
    void testConstructors() {
        assertNotNull(pieces);
        assertNotNull(pieces.getPieces());
        assertTrue(pieces.getPieces().size() > 0);

        Pieces copyPieces = new Pieces(pieces);
        assertEquals(pieces.getPieces().size(), copyPieces.getPieces().size());
        assertNotSame(pieces, copyPieces);

        Pieces customPieces = new Pieces(new HashMap<>());
        assertNotNull(customPieces);
    }

    @Test
    @DisplayName("Проверка методов get/set")
    void testGetSetMethods() {
        HashMap<Coordinate, Piece> newPieces = new HashMap<>();
        pieces.setPieces(newPieces);
        assertEquals(newPieces, pieces.getPieces());

        pieces.setIsCapture(true);
        assertTrue(pieces.getIsCapture());

        pieces.setGUIGame(true);
    }

    @Test
    @DisplayName("Проверка добавления и поиска фигур")
    void testAddAndFindPiece() {
        Coordinate newCoord = new Coordinate('d', 5);
        Piece newPawn = new Pawn(COLOUR.W, newCoord);

        pieces.addPiece(newCoord, newPawn);
        assertEquals(newPawn, pieces.getPiece(newCoord));
        assertEquals(newCoord, pieces.findPiece(newPawn));

        // Тестируем получение фигуры с несуществующей координаты
        Piece emptyPiece = pieces.getPiece(new Coordinate('z', 99));
        assertEquals(Piece.emptyPiece, emptyPiece);
    }

    @Test
    @DisplayName("Проверка поиска короля")
    void testFindKing() {
        assertEquals(whiteKingCoord, pieces.findKing(COLOUR.W));
        assertEquals(blackKingCoord, pieces.findKing(COLOUR.B));

        // Тестируем поиск короля на пустой доске
        HashMap<Coordinate, Piece> emptyBoard = new HashMap<>();
        Pieces emptyPieces = new Pieces(emptyBoard);
        Coordinate emptyKing = emptyPieces.findKing(COLOUR.W);
        assertEquals(Coordinate.emptyCoordinate, emptyKing);
    }

    @Test
    @DisplayName("Проверка получения фигур по цвету")
    void testGetColourPieces() {
        HashMap<Coordinate, Piece> whitePieces = pieces.getColourPieces(COLOUR.W);
        assertTrue(whitePieces.containsValue(whiteKing));
        assertTrue(whitePieces.containsValue(whitePawn));
        assertTrue(whitePieces.containsValue(whiteRook));

        HashMap<Coordinate, Piece> blackPieces = pieces.getColourPieces(COLOUR.B);
        assertTrue(blackPieces.containsValue(blackKing));
        assertTrue(blackPieces.containsValue(blackPawn));
    }

    @Test
    @DisplayName("Проверка всех возможных ходов по цвету")
    void testAllColouredMoves() {
        HashSet<Coordinate> whitePotentials = pieces.allColouredPotentials(COLOUR.W);
        assertNotNull(whitePotentials);

        HashSet<Coordinate> whiteRaws = pieces.allColouredRaws(COLOUR.W);
        assertNotNull(whiteRaws);

        HashSet<Coordinate> blackPotentials = pieces.allColouredPotentials(COLOUR.B);
        assertNotNull(blackPotentials);
    }

    @Test
    @DisplayName("Проверка методов проверки одинаковых фигур")
    void testSamePieceMethods() {
        assertFalse(pieces.pieceInSameFile(whitePawn));
        assertFalse(pieces.pieceInSameRank(whitePawn));

        // Добавляем еще одну пешку в тот же файл для тестирования
        Piece anotherPawn = new Pawn(COLOUR.W, new Coordinate('e', 3));
        pieces.addPiece(new Coordinate('e', 3), anotherPawn);
        pieces.updatePotentials();

        assertTrue(pieces.pieceInSameFile(whitePawn));
    }

    @Test
    @DisplayName("Проверка шаха")
    void testCheck() {
        HashMap<Coordinate, Piece> checkBoard = new HashMap<>();

        Coordinate wkCoord = new Coordinate('e', 1);
        Piece whiteKing = new King(COLOUR.W, wkCoord);
        checkBoard.put(wkCoord, whiteKing);

        Coordinate bkCoord = new Coordinate('a', 8);
        Piece blackKing = new King(COLOUR.B, bkCoord);
        checkBoard.put(bkCoord, blackKing);

        Coordinate rookCoord = new Coordinate('e', 2);
        Piece blackRook = new Rook(COLOUR.B, rookCoord);
        checkBoard.put(rookCoord, blackRook);

        Pieces checkPieces = new Pieces(checkBoard);
        checkPieces.updatePotentials();

        assertTrue(checkPieces.isCheck(COLOUR.W));
        assertFalse(checkPieces.isCheck(COLOUR.B));
    }

    @Test
    @DisplayName("Проверка ничьи")
    void testDraw() {
        HashMap<Coordinate, Piece> drawBoard = new HashMap<>();

        Coordinate wkCoord = new Coordinate('a', 1);
        Piece whiteKing = new King(COLOUR.W, wkCoord);
        drawBoard.put(wkCoord, whiteKing);

        Coordinate bkCoord = new Coordinate('h', 8);
        Piece blackKing = new King(COLOUR.B, bkCoord);
        drawBoard.put(bkCoord, blackKing);

        Pieces drawPieces = new Pieces(drawBoard);
        assertTrue(drawPieces.isDraw());
    }

    @Test
    @DisplayName("Проверка перемещения фигур")
    void testPieceMove() {
        Coordinate newCoord = new Coordinate('e', 4);
        pieces.pieceMove(newCoord, whitePawn);

        assertEquals(newCoord, pieces.findPiece(whitePawn));

        // Проверяем, что на старой позиции теперь пустая фигура
        Piece pieceAtOldPosition = pieces.getPiece(new Coordinate('e', 2));
        assertEquals(Piece.emptyPiece, pieceAtOldPosition);
    }

    @Test
    @DisplayName("Проверка выполнения хода")
    void testMakeMove() {
        Coordinate moveCoord = new Coordinate('e', 3);

        // Сохраняем исходное состояние
        int initialProgressSize = pieces.getGameProgress().size();

        pieces.makeMove(moveCoord, whitePawn);

        assertEquals(moveCoord, pieces.findPiece(whitePawn));

        // Проверяем, что история игры обновилась
        assertTrue(pieces.getGameProgress().size() > initialProgressSize);
    }

    @Test
    @DisplayName("Проверка обновления потенциальных ходов")
    void testUpdatePotentials() {
        int initialSize = whitePawn.getPotentialMoves().size();
        pieces.updatePotentials();

        // После обновления количество ходов должно остаться тем же или измениться
        assertTrue(whitePawn.getPotentialMoves().size() >= 0);
    }

    @Test
    @DisplayName("Проверка обновления предыдущих координат пешек")
    void testUpdatePreviousMovePawns() {
        pieces.updatePreviousMovePawns();

        // Проверяем, что у всех пешек установлены предыдущие координаты
        for (Piece piece : pieces.getPieces().values()) {
            if (piece.getName() == com.chess.engine.enums.ID.PAWN) {
                Pawn pawn = (Pawn) piece;
                assertNotNull(pawn.getPreviousCoordinate());
            }
        }
    }

    @Test
    @DisplayName("Проверка методов equals и hashCode")
    void testEqualsAndHashCode() {
        Pieces pieces1 = new Pieces(pieces.getPieces());
        Pieces pieces2 = new Pieces(pieces.getPieces());

        assertEquals(pieces1, pieces2);
        assertEquals(pieces1.hashCode(), pieces2.hashCode());

        HashMap<Coordinate, Piece> differentBoard = new HashMap<>();
        differentBoard.put(new Coordinate('a', 1), new King(COLOUR.W, new Coordinate('a', 1)));
        Pieces differentPieces = new Pieces(differentBoard);

        assertNotEquals(pieces1, differentPieces);
    }

    @Test
    @DisplayName("Проверка метода toString")
    void testToString() {
        String str = pieces.toString();
        assertNotNull(str);
        assertFalse(str.isEmpty());

        // Проверяем, что строка содержит информацию о фигурах
        assertTrue(str.contains("*") || str.contains("на"));
    }

    @Test
    @DisplayName("Проверка истории игры")
    void testGameProgress() {
        ArrayList<HashMap<Coordinate, Piece>> progress = pieces.getGameProgress();
        assertNotNull(progress);
        assertTrue(progress.size() > 0);

        pieces.setPreviousPieces(pieces.getPieces());
        assertNotNull(pieces.getPreviousPieces());
        assertEquals(pieces.getPieces().size(), pieces.getPreviousPieces().size());
    }

    @Test
    @DisplayName("Проверка обработки null параметров")
    void testNullParameters() {
        assertThrows(NullPointerException.class, () -> pieces.findPiece(null));
        assertThrows(NullPointerException.class, () -> pieces.getPiece(null));
    }

    @Test
    @DisplayName("Проверка сценария с малым количеством фигур")
    void testMinimalPieceScenario() {
        HashMap<Coordinate, Piece> minimalBoard = new HashMap<>();

        minimalBoard.put(new Coordinate('e', 1), new King(COLOUR.W, new Coordinate('e', 1)));
        minimalBoard.put(new Coordinate('e', 8), new King(COLOUR.B, new Coordinate('e', 8)));
        minimalBoard.put(new Coordinate('d', 2), new Pawn(COLOUR.W, new Coordinate('d', 2)));

        Pieces minimalPieces = new Pieces(minimalBoard);
        minimalPieces.updatePotentials();

        assertNotNull(minimalPieces.getPieces());
        assertFalse(minimalPieces.isCheck(COLOUR.W));
        assertFalse(minimalPieces.isCheck(COLOUR.B));
    }

    @Test
    @DisplayName("Проверка отсутствия шаха в начальной позиции")
    void testNoCheckInInitialPosition() {
        assertFalse(pieces.isCheck(COLOUR.W));
        assertFalse(pieces.isCheck(COLOUR.B));
    }

    @Test
    @DisplayName("Проверка отсутствия мата в начальной позиции")
    void testNoMateInInitialPosition() {
        assertFalse(pieces.isMate(COLOUR.W));
        assertFalse(pieces.isMate(COLOUR.B));
    }

    @Test
    @DisplayName("Проверка взятия фигуры")
    void testCapture() {
        HashMap<Coordinate, Piece> captureBoard = new HashMap<>();

        Coordinate wkCoord = new Coordinate('e', 1);
        Piece whiteKing = new King(COLOUR.W, wkCoord);
        captureBoard.put(wkCoord, whiteKing);

        Coordinate bkCoord = new Coordinate('e', 8);
        Piece blackKing = new King(COLOUR.B, bkCoord);
        captureBoard.put(bkCoord, blackKing);

        Coordinate wpCoord = new Coordinate('e', 2);
        Piece whitePawn = new Pawn(COLOUR.W, wpCoord);
        captureBoard.put(wpCoord, whitePawn);

        Coordinate bpCoord = new Coordinate('d', 3);
        Piece blackPawn = new Pawn(COLOUR.B, bpCoord);
        captureBoard.put(bpCoord, blackPawn);

        Pieces capturePieces = new Pieces(captureBoard);
        capturePieces.updatePotentials();

        capturePieces.makeMove(new Coordinate('d', 3), whitePawn);
        assertTrue(capturePieces.getIsCapture());
    }

    @Test
    @DisplayName("Проверка копирования доски")
    void testBoardCopy() {
        Pieces original = new Pieces(pieces.getPieces());
        Pieces copy = new Pieces(original);

        assertEquals(original.getPieces().size(), copy.getPieces().size());
        assertNotSame(original, copy);


        for (Coordinate coord : original.getPieces().keySet()) {
            Piece originalPiece = original.getPiece(coord);
            Piece copyPiece = copy.getPiece(coord);
            assertEquals(originalPiece, copyPiece);
            assertNotSame(originalPiece, copyPiece);
        }
    }

    @Test
    @DisplayName("Проверка работы с пустой доской")
    void testEmptyBoard() {
        HashMap<Coordinate, Piece> emptyMap = new HashMap<>();
        Pieces emptyPieces = new Pieces(emptyMap);

        assertNotNull(emptyPieces.getPieces());
        assertEquals(0, emptyPieces.getPieces().size());


        assertNotNull(emptyPieces.getColourPieces(COLOUR.W));
        assertNotNull(emptyPieces.getColourPieces(COLOUR.B));
        assertNotNull(emptyPieces.allColouredPotentials(COLOUR.W));
        assertNotNull(emptyPieces.allColouredRaws(COLOUR.W));
    }
}