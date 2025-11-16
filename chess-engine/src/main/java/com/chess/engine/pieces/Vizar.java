package com.chess.engine.pieces;

import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Move;
import com.chess.engine.logic.Pieces;
import com.chess.engine.enums.COLOUR;
import com.chess.engine.enums.ID;

import java.util.ArrayList;

/**
 * Класс, представляющий визиря в шахматах (специальная фигура)
 * Визирь ходит как конь и слон одновременно
 */
public class Vizar extends Piece{

    /**
     * Конструктор визиря
     * @param colour цвет визиря
     * @param OGcoord исходная координата визара
     */
    public Vizar(COLOUR colour, Coordinate OGcoord) {
        super(ID.VIZAR, colour, OGcoord);
    }

    /**
     * Конструктор копирования визиря
     * @param original оригинальный визирь для копирования
     */
    public Vizar(Vizar original) {
        super(original);
    }

    @Override
    public Vizar makeCopy() {
        return new Vizar(this);
    }

    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        ArrayList<Coordinate> knightMoves = Move.frontKnight(pieces, this);
        knightMoves.addAll(Move.backKnight(pieces, this));
        knightMoves.addAll(Move.rightKnight(pieces, this));
        knightMoves.addAll(Move.leftKnight(pieces, this));

        ArrayList<Coordinate> bishopMoves = Move.frontRDigFree(pieces, this, dimension);
        bishopMoves.addAll(Move.backRDigFree(pieces, this, dimension));
        bishopMoves.addAll(Move.backLDigFree(pieces, this, dimension));
        bishopMoves.addAll(Move.frontLDigFree(pieces, this, dimension));

        ArrayList<Coordinate> giraffeMoves = new ArrayList<>();
        giraffeMoves.addAll(knightMoves);
        giraffeMoves.addAll(bishopMoves);

        return giraffeMoves;
    }

}