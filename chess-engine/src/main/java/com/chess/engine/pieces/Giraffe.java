package com.chess.engine.pieces;

import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Move;
import com.chess.engine.logic.Pieces;
import com.chess.engine.enums.COLOUR;
import com.chess.engine.enums.ID;

import java.util.ArrayList;

/**
 * Класс, представляющий жирафа в шахматах (специальная фигура)
 * Жираф ходит как ладья, конь и слон одновременно
 */
public class Giraffe extends Piece {

    /**
     * Конструктор жирафа
     * @param colour цвет жирафа
     * @param OGcoord исходная координата жирафа
     */
    public Giraffe(COLOUR colour, Coordinate OGcoord) {
        super(ID.GIRAFFE, colour, OGcoord);
    }

    /**
     * Конструктор копирования жирафа
     * @param original оригинальный жираф для копирования
     */
    public Giraffe(Giraffe original) {
        super(original);
    }

    @Override
    public Giraffe makeCopy() {
        return new Giraffe(this);
    }

    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {
        ArrayList<Coordinate> rookMoves = Move.frontFree(pieces, this, dimension);
        rookMoves.addAll(Move.rightFree(pieces, this, dimension));
        rookMoves.addAll(Move.backFree(pieces, this, dimension));
        rookMoves.addAll(Move.leftFree(pieces, this, dimension));

        ArrayList<Coordinate> knightMoves = Move.frontKnight(pieces, this);
        knightMoves.addAll(Move.backKnight(pieces, this));
        knightMoves.addAll(Move.rightKnight(pieces, this));
        knightMoves.addAll(Move.leftKnight(pieces, this));

        ArrayList<Coordinate> bishopMoves = Move.frontRDigFree(pieces, this, dimension);
        bishopMoves.addAll(Move.backRDigFree(pieces, this, dimension));
        bishopMoves.addAll(Move.backLDigFree(pieces, this, dimension));
        bishopMoves.addAll(Move.frontLDigFree(pieces, this, dimension));

        ArrayList<Coordinate> giraffeMoves = new ArrayList<>();
        giraffeMoves.addAll(rookMoves);
        giraffeMoves.addAll(knightMoves);
        giraffeMoves.addAll(bishopMoves);

        return giraffeMoves;
    }

}