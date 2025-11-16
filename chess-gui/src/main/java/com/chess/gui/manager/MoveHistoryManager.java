package com.chess.gui.manager;

import com.chess.engine.notation.ChessIO;
import com.chess.engine.enums.COLOUR;
import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Pieces;
import com.chess.engine.pieces.Piece;

public class MoveHistoryManager {
    private int numberOfTurns = 0;
    private final StringBuilder moveHistory = new StringBuilder();

    public void recordMove(Coordinate coordinate, Piece piece, Pieces pieces, COLOUR turn) {
        String moveString = ChessIO.moveString(pieces, coordinate, piece);

        if (turn == COLOUR.W) {
            numberOfTurns++;
            moveHistory.append(numberOfTurns).append(". ").append(moveString).append(" ");
        } else {
            moveHistory.append(moveString).append(" ");
        }

    }

    public String getFullMoveHistory() {
        return moveHistory.toString();
    }

}