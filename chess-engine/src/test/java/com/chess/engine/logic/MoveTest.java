package com.chess.engine.logic;

import com.chess.engine.enums.COLOUR;
import com.chess.engine.enums.ID;
import com.chess.engine.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    private Pieces pieces;
    private Piece whitePawn;
    private Piece blackPawn;
    private Piece whiteRook;
    private Piece blackBishop;
    private Piece whiteKnight;
    private Piece whiteKing;
    private Piece blackKing;
    private Coordinate whitePawnCoord;
    private Coordinate blackPawnCoord;

    @BeforeEach
    void setUp() {
        HashMap<Coordinate, Piece> testBoard = new HashMap<>();

        Coordinate whiteKingCoord = new Coordinate('e', 1);
        whiteKing = new King(COLOUR.W, whiteKingCoord);
        testBoard.put(whiteKingCoord, whiteKing);

        Coordinate blackKingCoord = new Coordinate('e', 8);
        blackKing = new King(COLOUR.B, blackKingCoord);
        testBoard.put(blackKingCoord, blackKing);

        whitePawnCoord = new Coordinate('e', 2);
        whitePawn = new Pawn(COLOUR.W, whitePawnCoord);
        testBoard.put(whitePawnCoord, whitePawn);

        blackPawnCoord = new Coordinate('e', 7);
        blackPawn = new Pawn(COLOUR.B, blackPawnCoord);
        testBoard.put(blackPawnCoord, blackPawn);

        Coordinate whiteRookCoord = new Coordinate('a', 1);
        whiteRook = new Rook(COLOUR.W, whiteRookCoord);
        testBoard.put(whiteRookCoord, whiteRook);

        Coordinate blackBishopCoord = new Coordinate('f', 8);
        blackBishop = new Bishop(COLOUR.B, blackBishopCoord);
        testBoard.put(blackBishopCoord, blackBishop);

        Coordinate whiteKnightCoord = new Coordinate('g', 1);
        whiteKnight = new Knight(COLOUR.W, whiteKnightCoord);
        testBoard.put(whiteKnightCoord, whiteKnight);

        pieces = new Pieces(testBoard);
        pieces.updatePotentials();
    }

    @Test
    @DisplayName("Проверка базовых методов Move")
    void testBasicMoveMethods() {
        Coordinate emptyCoord = new Coordinate('d', 4);

        assertFalse(Move.tileFull(pieces, emptyCoord));
        assertTrue(Move.tileFull(pieces, whitePawnCoord));
        assertTrue(Move.isNotTileColour(pieces, blackPawnCoord, COLOUR.W));
        assertFalse(Move.isNotTileColour(pieces, whitePawnCoord, COLOUR.W));
    }

    @Test
    @DisplayName("Проверка ходов пешки вперед")
    void testFrontFree() {
        ArrayList<Coordinate> whiteMoves = Move.frontFree(pieces, whitePawn, 2);
        assertEquals(2, whiteMoves.size());
        assertTrue(whiteMoves.contains(new Coordinate('e', 3)));
        assertTrue(whiteMoves.contains(new Coordinate('e', 4)));

        ArrayList<Coordinate> blackMoves = Move.frontFree(pieces, blackPawn, 2);
        assertEquals(2, blackMoves.size());
        assertTrue(blackMoves.contains(new Coordinate('e', 6)));
        assertTrue(blackMoves.contains(new Coordinate('e', 5)));
    }

    @Test
    @DisplayName("Проверка ходов пешки назад")
    void testBackFree() {
        ArrayList<Coordinate> whiteMoves = Move.backFree(pieces, whitePawn, 1);
        assertEquals(0, whiteMoves.size());

        ArrayList<Coordinate> blackMoves = Move.backFree(pieces, blackPawn, 1);
        assertEquals(0, blackMoves.size());
    }

    @Test
    @DisplayName("Проверка боковых ходов ладьи")
    void testHorizontalMoves() {
        ArrayList<Coordinate> rightMoves = Move.rightFree(pieces, whiteRook, 9);
        assertTrue(rightMoves.size() > 0);
        assertTrue(rightMoves.contains(new Coordinate('b', 1)));

        ArrayList<Coordinate> leftMoves = Move.leftFree(pieces, whiteRook, 1);
        assertEquals(0, leftMoves.size());
    }

    @Test
    @DisplayName("Проверка диагональных ходов слона")
    void testDiagonalMoves() {
        ArrayList<Coordinate> frontRightMoves = Move.frontRDigFree(pieces, blackBishop, 2);
        ArrayList<Coordinate> frontLeftMoves = Move.frontLDigFree(pieces, blackBishop, 2);

        assertTrue(frontRightMoves.size() > 0 || frontLeftMoves.size() > 0);
    }

    @Test
    @DisplayName("Проверка ходов коня")
    void testKnightMoves() {
        ArrayList<Coordinate> frontKnightMoves = Move.frontKnight(pieces, whiteKnight);
        assertTrue(frontKnightMoves.size() >= 1);
        assertTrue(frontKnightMoves.contains(new Coordinate('f', 3)) ||
                frontKnightMoves.contains(new Coordinate('h', 3)));

        ArrayList<Coordinate> rightKnightMoves = Move.rightKnight(pieces, whiteKnight);
        assertTrue(rightKnightMoves.size() >= 1);
    }

    @Test
    @DisplayName("Проверка обработки null параметров")
    void testNullParameters() {
        Coordinate validCoord = new Coordinate('e', 4);

        assertThrows(NullPointerException.class, () -> Move.tileFull(null, validCoord));
        assertThrows(NullPointerException.class, () -> Move.tileFull(pieces, null));
        assertThrows(NullPointerException.class, () -> Move.isNotTileColour(null, validCoord, COLOUR.W));
        assertThrows(NullPointerException.class, () -> Move.isNotTileColour(pieces, null, COLOUR.W));
        assertThrows(NullPointerException.class, () -> Move.frontFree(null, whitePawn, 1));
        assertThrows(NullPointerException.class, () -> Move.frontFree(pieces, null, 1));
    }

    @Test
    @DisplayName("Проверка взятия фигур")
    void testCaptureMoves() {
        Coordinate captureCoord = new Coordinate('e', 3);
        Piece blackPiece = new Pawn(COLOUR.B, captureCoord);
        pieces.addPiece(captureCoord, blackPiece);
        pieces.updatePotentials();

        ArrayList<Coordinate> moves = Move.frontFree(pieces, whitePawn, 2);
        assertTrue(moves.size() >= 1);
        assertTrue(moves.contains(captureCoord));
    }

    @Test
    @DisplayName("Проверка блокировки ходов своей фигурой")
    void testBlockedByOwnPiece() {
        Coordinate blockCoord = new Coordinate('e', 3);
        Piece whitePiece = new Pawn(COLOUR.W, blockCoord);
        pieces.addPiece(blockCoord, whitePiece);
        pieces.updatePotentials();

        ArrayList<Coordinate> moves = Move.frontFree(pieces, whitePawn, 2);
        assertEquals(0, moves.size());
    }

    @Test
    @DisplayName("Проверка границ доски")
    void testBoardBoundaries() {
        Piece edgeRook = new Rook(COLOUR.W, new Coordinate('a', 1));

        ArrayList<Coordinate> leftMoves = Move.leftFree(pieces, edgeRook, 1);
        assertEquals(0, leftMoves.size());

        ArrayList<Coordinate> backMoves = Move.backFree(pieces, edgeRook, 1);
        assertEquals(0, backMoves.size());
    }

    @Test
    @DisplayName("Проверка координат")
    void testCoordinate() {
        Coordinate coord1 = new Coordinate('e', 4);
        Coordinate coord2 = new Coordinate("e4");
        Coordinate coord3 = new Coordinate(coord1);

        assertEquals(coord1, coord2);
        assertEquals(coord1, coord3);
        assertEquals('e', coord1.getFile());
        assertEquals(4, coord1.getRank());

        assertTrue(Coordinate.inBoard(new Coordinate('a', 1)));
        assertTrue(Coordinate.inBoard(new Coordinate('j', 10)));
        assertFalse(Coordinate.inBoard(new Coordinate('a', 0)));
        assertFalse(Coordinate.inBoard(new Coordinate('k', 5)));

        Coordinate fromString = new Coordinate("h8");
        assertEquals('h', fromString.getFile());
        assertEquals(8, fromString.getRank());

        Coordinate invalid = new Coordinate("invalid");
        assertEquals(0, invalid.getFile());
        assertEquals(0, invalid.getRank());

        assertEquals("e4", coord1.toString());
    }

    @Test
    @DisplayName("Проверка управления фигурами")
    void testPiecesManagement() {
        Piece foundPiece = pieces.getPiece(whitePawnCoord);
        assertEquals(whitePawn, foundPiece);

        Coordinate foundCoord = pieces.findPiece(whitePawn);
        assertEquals(whitePawnCoord, foundCoord);

        HashMap<Coordinate, Piece> whitePieces = pieces.getColourPieces(COLOUR.W);
        assertTrue(whitePieces.containsValue(whitePawn));
        assertTrue(whitePieces.containsValue(whiteRook));
        assertTrue(whitePieces.containsValue(whiteKing));

        Coordinate whiteKingCoord = pieces.findKing(COLOUR.W);
        assertEquals(new Coordinate('e', 1), whiteKingCoord);

        Coordinate blackKingCoord = pieces.findKing(COLOUR.B);
        assertEquals(new Coordinate('e', 8), blackKingCoord);

        Coordinate newCoord = new Coordinate('e', 4);
        pieces.pieceMove(newCoord, whitePawn);
        assertEquals(newCoord, pieces.findPiece(whitePawn));

        Piece pieceAfterMove = pieces.getPiece(whitePawnCoord);
        assertEquals(Piece.emptyPiece, pieceAfterMove);
    }

    @Test
    @DisplayName("Проверка проверки шаха")
    void testCheck() {
        HashMap<Coordinate, Piece> checkBoard = new HashMap<>();

        Coordinate whiteKingCoord = new Coordinate('e', 1);
        Piece whiteKing = new King(COLOUR.W, whiteKingCoord);
        checkBoard.put(whiteKingCoord, whiteKing);

        Coordinate blackRookCoord = new Coordinate('e', 8);
        Piece blackRook = new Rook(COLOUR.B, blackRookCoord);
        checkBoard.put(blackRookCoord, blackRook);

        Coordinate blackKingCoord = new Coordinate('a', 8);
        Piece blackKing = new King(COLOUR.B, blackKingCoord);
        checkBoard.put(blackKingCoord, blackKing);

        Pieces checkPieces = new Pieces(checkBoard);
        checkPieces.updatePotentials();

        assertTrue(checkPieces.isCheck(COLOUR.W));
        assertFalse(checkPieces.isCheck(COLOUR.B));
    }

    @Test
    @DisplayName("Проверка особых случаев пешки")
    void testPawnSpecialCases() {
        Pawn pawnOnSecondRank = new Pawn(COLOUR.W, new Coordinate('d', 2));
        pieces.addPiece(new Coordinate('d', 2), pawnOnSecondRank);
        pieces.updatePotentials();

        ArrayList<Coordinate> moves = Move.frontFree(pieces, pawnOnSecondRank, 2);
        assertEquals(2, moves.size());

        Pawn pawnOnSeventhRank = new Pawn(COLOUR.B, new Coordinate('d', 7));
        pieces.addPiece(new Coordinate('d', 7), pawnOnSeventhRank);
        pieces.updatePotentials();

        ArrayList<Coordinate> blackMoves = Move.frontFree(pieces, pawnOnSeventhRank, 2);
        assertEquals(2, blackMoves.size());
    }

    @Test
    @DisplayName("Проверка всех возможных ходов фигур")
    void testAllPossibleMoves() {
        assertTrue(whitePawn.getPotentialMoves().size() > 0);
        assertTrue(blackPawn.getPotentialMoves().size() > 0);
        assertTrue(whiteRook.getPotentialMoves().size() > 0);
        assertTrue(whiteKnight.getPotentialMoves().size() > 0);
        assertTrue(whiteKing.getPotentialMoves().size() > 0);
        assertTrue(blackKing.getPotentialMoves().size() > 0);
    }

    @Test
    @DisplayName("Проверка копирования объектов")
    void testCopyConstructors() {
        Coordinate originalCoord = new Coordinate('e', 4);
        Coordinate copiedCoord = new Coordinate(originalCoord);
        assertEquals(originalCoord, copiedCoord);
        assertNotSame(originalCoord, copiedCoord);

        Pieces copiedPieces = new Pieces(pieces);
        assertEquals(pieces.getPieces().size(), copiedPieces.getPieces().size());
        assertNotSame(pieces, copiedPieces);

        Piece copiedPawn = whitePawn.makeCopy();
        assertEquals(whitePawn, copiedPawn);
        assertNotSame(whitePawn, copiedPawn);
    }

    @Test
    @DisplayName("Проверка ограничения ходов при блокировке")
    void testMoveLimitation() {
        HashMap<Coordinate, Piece> isolatedBoard = new HashMap<>();

        isolatedBoard.put(new Coordinate('e', 1), new King(COLOUR.W, new Coordinate('e', 1)));
        isolatedBoard.put(new Coordinate('e', 8), new King(COLOUR.B, new Coordinate('e', 8)));

        Coordinate rookCoord = new Coordinate('d', 5);
        Piece isolatedRook = new Rook(COLOUR.W, rookCoord);
        isolatedBoard.put(rookCoord, isolatedRook);

        isolatedBoard.put(new Coordinate('d', 6), new Pawn(COLOUR.W, new Coordinate('d', 6)));
        isolatedBoard.put(new Coordinate('d', 4), new Pawn(COLOUR.W, new Coordinate('d', 4)));
        isolatedBoard.put(new Coordinate('c', 5), new Pawn(COLOUR.W, new Coordinate('c', 5)));
        isolatedBoard.put(new Coordinate('e', 5), new Pawn(COLOUR.W, new Coordinate('e', 5)));

        Pieces isolatedPieces = new Pieces(isolatedBoard);
        isolatedPieces.updatePotentials();

        assertEquals(0, isolatedRook.getPotentialMoves().size());
    }

    @Test
    @DisplayName("Проверка пустых фигур и координат")
    void testEmptyPiecesAndCoordinates() {
        assertEquals(0, Coordinate.emptyCoordinate.getFile());
        assertEquals(0, Coordinate.emptyCoordinate.getRank());
        assertEquals(Piece.emptyPiece, pieces.getPiece(new Coordinate('z', 99)));
        assertEquals(ID.ROOK, Piece.emptyPiece.getName());
        assertEquals(COLOUR.W, Piece.emptyPiece.getColour());
    }

    @Test
    @DisplayName("Проверка обновления потенциальных ходов")
    void testUpdatePotentials() {
        int initialMoves = whitePawn.getPotentialMoves().size();

        Coordinate blockCoord = new Coordinate('e', 3);
        Piece blocker = new Pawn(COLOUR.B, blockCoord);
        pieces.addPiece(blockCoord, blocker);

        pieces.updatePotentials();

        assertTrue(whitePawn.getPotentialMoves().size() < initialMoves);
    }

    @Test
    @DisplayName("Проверка ходов короля")
    void testKingMoves() {
        ArrayList<Coordinate> kingMoves = new ArrayList<>();

        kingMoves.addAll(Move.frontFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.backFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.rightFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.leftFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.frontRDigFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.frontLDigFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.backRDigFree(pieces, whiteKing, 1));
        kingMoves.addAll(Move.backLDigFree(pieces, whiteKing, 1));

        assertTrue(kingMoves.size() >= 3);
    }

    @Test
    @DisplayName("Проверка комплексных ходов фигур")
    void testComplexPieceMoves() {
        ArrayList<Coordinate> pawnRawMoves = whitePawn.getRawMoves(pieces);
        ArrayList<Coordinate> rookRawMoves = whiteRook.getRawMoves(pieces);
        ArrayList<Coordinate> knightRawMoves = whiteKnight.getRawMoves(pieces);
        ArrayList<Coordinate> bishopRawMoves = blackBishop.getRawMoves(pieces);
        ArrayList<Coordinate> kingRawMoves = whiteKing.getRawMoves(pieces);

        assertTrue(pawnRawMoves.size() > 0);
        assertTrue(rookRawMoves.size() > 0);
        assertTrue(knightRawMoves.size() > 0);
        assertTrue(bishopRawMoves.size() > 0);
        assertTrue(kingRawMoves.size() > 0);

        int pawnPotentialMoves = whitePawn.getPotentialMoves().size();
        int pawnRawMovesCount = pawnRawMoves.size();

        assertTrue(pawnPotentialMoves <= pawnRawMovesCount);
    }

    @Test
    @DisplayName("Проверка всех диагональных направлений")
    void testAllDiagonalDirections() {
        Piece testPiece = new Bishop(COLOUR.W, new Coordinate('e', 5));

        ArrayList<Coordinate> frontRight = Move.frontRDigFree(pieces, testPiece, 3);
        ArrayList<Coordinate> frontLeft = Move.frontLDigFree(pieces, testPiece, 3);
        ArrayList<Coordinate> backRight = Move.backRDigFree(pieces, testPiece, 3);
        ArrayList<Coordinate> backLeft = Move.backLDigFree(pieces, testPiece, 3);

        assertTrue(frontRight.size() > 0 || frontLeft.size() > 0 ||
                backRight.size() > 0 || backLeft.size() > 0);
    }

    @Test
    @DisplayName("Проверка равенства и хэш-кода")
    void testEqualsAndHashCode() {
        Coordinate coord1 = new Coordinate('e', 4);
        Coordinate coord2 = new Coordinate('e', 4);
        Coordinate coord3 = new Coordinate('d', 4);

        assertEquals(coord1, coord2);
        assertNotEquals(coord1, coord3);
        assertEquals(coord1.hashCode(), coord2.hashCode());

        Piece pawn1 = new Pawn(COLOUR.W, new Coordinate('e', 2));
        Piece pawn2 = new Pawn(COLOUR.W, new Coordinate('e', 2));

        assertEquals(pawn1, pawn2);
        assertEquals(pawn1.hashCode(), pawn2.hashCode());
    }

    @Test
    @DisplayName("Проверка методов toString")
    void testToStringMethods() {
        Coordinate coord = new Coordinate('e', 4);
        assertEquals("e4", coord.toString());

        String pieceString = whitePawn.toString();
        assertTrue(pieceString.contains("e2"));
    }
}