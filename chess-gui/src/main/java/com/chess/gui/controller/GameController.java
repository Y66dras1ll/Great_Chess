package com.chess.gui.controller;

import com.chess.engine.enums.COLOUR;
import com.chess.engine.enums.ID;
import com.chess.gui.handlers.GameSaveHandler;
import com.chess.gui.handlers.PawnPromotionHandler;
import com.chess.gui.panels.BoardPanel;
import com.chess.gui.panels.InfoPanel;
import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Pieces;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;


public class GameController {
    private final Pieces pieces;
    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel;

    private COLOUR currentTurn = COLOUR.W;
    private Piece selectedPiece;
    private int clickCounter = 0;
    private boolean gameActive = true;

    public GameController(Pieces pieces, BoardPanel boardPanel, InfoPanel infoPanel) {
        this.pieces = pieces;
        this.boardPanel = boardPanel;
        this.infoPanel = infoPanel;
    }

    public void handleTileClick(Coordinate coordinate) {
        if (!gameActive) return; // Игнорируем клики если игра завершена

        Piece clickedPiece = pieces.getPieces().get(coordinate);

        if (clickCounter == 0) {
            // Первый клик - выбор фигуры
            if (clickedPiece != null && clickedPiece.getColour() == currentTurn) {
                selectPiece(clickedPiece);
            }
        } else {
            // Второй клик - попытка хода
            if (selectedPiece != null && selectedPiece.isValidMove(coordinate, currentTurn)) {
                handleMove(coordinate);
            } else {
                // Если кликнули на другую свою фигуру - выбираем её
                if (clickedPiece != null && clickedPiece.getColour() == currentTurn) {
                    selectPiece(clickedPiece);
                } else {
                    resetSelection();
                }
            }
        }
    }

    private void selectPiece(Piece piece) {
        this.selectedPiece = piece;
        this.clickCounter = 1;
        boardPanel.highlightPossibleMoves(piece.getPotentialMoves());
    }

    private void handleMove(Coordinate targetCoordinate) {
        // Обработка продвижения пешки
        if (selectedPiece.getName() == ID.PAWN) {
            Pawn pawn = (Pawn) selectedPiece;
            if (pawn.canPromoteBlack(targetCoordinate) || pawn.canPromoteWhite(targetCoordinate)) {
                PawnPromotionHandler.handlePawnPromotion(pawn, targetCoordinate);
            }
        }

        // Выполняем ход
        pieces.makeMove(targetCoordinate, selectedPiece);

        // Обновляем UI
        boardPanel.updateBoard(pieces);
        infoPanel.recordMove(targetCoordinate, selectedPiece, pieces, currentTurn);

        // Меняем ход и проверяем состояние игры
        switchTurn();
        checkGameState();

        resetSelection();
    }

    private void resetSelection() {
        this.selectedPiece = null;
        this.clickCounter = 0;
        boardPanel.resetBoardColors();
    }

    private void switchTurn() {
        currentTurn = COLOUR.not(currentTurn);
    }

    private void checkGameState() {
        if (pieces.isMate(currentTurn)) {
            infoPanel.setGameResult(COLOUR.not(currentTurn).toString() + " выиграли, поставив мат.");
            endGame();
        } else if (pieces.isStalemate(COLOUR.not(currentTurn))) {
            infoPanel.setGameResult("Игра в ничью закончена.");
            endGame();
        } else if (pieces.isDraw()) {
            infoPanel.setGameResult("Ничья.");
            endGame();
        }
    }

    private void endGame() {
        gameActive = false;
        boardPanel.disableBoard();
    }

    public void saveGame() {
        GameSaveHandler.handleSaveGame(infoPanel.getMoveHistory());
    }
}