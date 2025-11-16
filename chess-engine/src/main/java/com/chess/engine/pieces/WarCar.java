package com.chess.engine.pieces;

import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Move;
import com.chess.engine.logic.Pieces;
import com.chess.engine.enums.COLOUR;
import com.chess.engine.enums.ID;

import java.util.ArrayList;

/**
 * Класс, представляющий боевую машину в шахматах (специальная фигура)
 * Военная колесница ходит как ладья и конь одновременно
 */
public class WarCar extends Piece {

    /**
     * Конструктор боевой машины
     * @param colour цвет машины
     * @param OGcoord исходная координата машины
     */
    public WarCar(COLOUR colour, Coordinate OGcoord) {
        super(ID.WARCAR, colour, OGcoord);
    }

    /**
     * Конструктор копирования боевой машины
     * @param original оригинальная колесница для копирования
     */
    public WarCar(WarCar original) {
        super(original);
    }

    @Override
    public WarCar makeCopy() {
        return new WarCar(this);
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

        ArrayList<Coordinate> giraffeMoves = new ArrayList<>();
        giraffeMoves.addAll(rookMoves);
        giraffeMoves.addAll(knightMoves);

        return giraffeMoves;
    }

}