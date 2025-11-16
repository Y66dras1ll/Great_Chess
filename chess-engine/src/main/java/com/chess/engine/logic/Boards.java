package com.chess.engine.logic;

import com.chess.engine.enums.COLOUR;
import com.chess.engine.pieces.*;

import java.util.HashMap;

/**
 * Класс для создания начальной позиции шахматной доски
 */
public class Boards {

    /**
     * Создает начальную позицию всех фигур на доске
     * @return карта координат и фигур для начальной позиции
     */
    public static HashMap<Coordinate, Piece> getChessBoard() {

        HashMap <Coordinate, Piece> pieces = new HashMap<>();

        int pawnBRank = 9;
        int bRank = 10;
        int pawnBRankTwo = 8;
        int pawnWRank = 2;
        int pawnWRankTwo = 3;
        int wRank = 1;


        Pawn pawnBa = new Pawn(COLOUR.B, new Coordinate('a', pawnBRank));
        Pawn pawnBb = new Pawn(COLOUR.B, new Coordinate('b', pawnBRank));
        Pawn pawnBc = new Pawn(COLOUR.B, new Coordinate('c', pawnBRank));
        Pawn pawnBd = new Pawn(COLOUR.B, new Coordinate('d', pawnBRank));
        Pawn pawnBe = new Pawn(COLOUR.B, new Coordinate('e', pawnBRankTwo));
        Pawn pawnBf = new Pawn(COLOUR.B, new Coordinate('f', pawnBRankTwo));
        Pawn pawnBg = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));
        Pawn pawnBh = new Pawn(COLOUR.B, new Coordinate('h', pawnBRank));
        Pawn pawnBi = new Pawn(COLOUR.B, new Coordinate('i', pawnBRank));
        Pawn pawnBj = new Pawn(COLOUR.B, new Coordinate('j', pawnBRank));

        Rook rookBa = new Rook(COLOUR.B, new Coordinate('a', bRank));
        Rook rookBj = new Rook(COLOUR.B, new Coordinate('j', bRank));

        Knight knightBb = new Knight(COLOUR.B, new Coordinate('b', bRank));
        Knight knightBi = new Knight(COLOUR.B, new Coordinate('i', bRank));

        Bishop bishopBc = new Bishop(COLOUR.B, new Coordinate('c', bRank));
        Bishop bishopBh = new Bishop(COLOUR.B, new Coordinate('h', bRank));

        Giraffe GiraffeB = new Giraffe(COLOUR.B, new Coordinate('e', bRank));

        Queen queenB = new Queen(COLOUR.B, new Coordinate('g', bRank));

        King kingB = new King(COLOUR.B, new Coordinate('f', bRank));

        Vizar VizarB = new Vizar(COLOUR.B, new Coordinate('d', bRank));

        WarCar WarCarBe = new WarCar(COLOUR.B, new Coordinate('e', pawnBRank));
        WarCar WarCarBf = new WarCar(COLOUR.B, new Coordinate('f', pawnBRank));

        Pawn pawnWa = new Pawn(COLOUR.W, new Coordinate('a', pawnWRank));
        Pawn pawnWb = new Pawn(COLOUR.W, new Coordinate('b', pawnWRank));
        Pawn pawnWc = new Pawn(COLOUR.W, new Coordinate('c', pawnWRank));
        Pawn pawnWd = new Pawn(COLOUR.W, new Coordinate('d', pawnWRank));
        Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRankTwo));
        Pawn pawnWf = new Pawn(COLOUR.W, new Coordinate('f', pawnWRankTwo));
        Pawn pawnWg = new Pawn(COLOUR.W, new Coordinate('g', pawnWRank));
        Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));
        Pawn pawnWi = new Pawn(COLOUR.W, new Coordinate('i', pawnWRank));
        Pawn pawnWj = new Pawn(COLOUR.W, new Coordinate('j', pawnWRank));

        Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
        Rook rookWj = new Rook(COLOUR.W, new Coordinate('j', wRank));

        Knight knightWb = new Knight(COLOUR.W, new Coordinate('b', wRank));
        Knight knightWi = new Knight(COLOUR.W, new Coordinate('i', wRank));

        Bishop bishopWc = new Bishop(COLOUR.W, new Coordinate('c', wRank));
        Bishop bishopWh = new Bishop(COLOUR.W, new Coordinate('h', wRank));

        Giraffe GiraffeW = new Giraffe(COLOUR.W, new Coordinate('e', wRank));

        Vizar VizarW = new Vizar(COLOUR.W, new Coordinate('d', wRank));

        WarCar WarCarWe = new WarCar(COLOUR.W, new Coordinate('e', pawnWRank));
        WarCar WarCarWf = new WarCar(COLOUR.W, new Coordinate('f', pawnWRank));

        Queen queenW = new Queen(COLOUR.W, new Coordinate('g', wRank));

        King kingW = new King(COLOUR.W, new Coordinate('f', wRank));


        pieces.put(pawnBa.getCoords(), pawnBa);
        pieces.put(pawnBb.getCoords(), pawnBb);
        pieces.put(pawnBc.getCoords(), pawnBc);
        pieces.put(pawnBd.getCoords(), pawnBd);
        pieces.put(pawnBe.getCoords(), pawnBe);
        pieces.put(pawnBf.getCoords(), pawnBf);
        pieces.put(pawnBg.getCoords(), pawnBg);
        pieces.put(pawnBh.getCoords(), pawnBh);
        pieces.put(pawnBi.getCoords(), pawnBi);
        pieces.put(pawnBj.getCoords(), pawnBj);

        pieces.put(rookBa.getCoords(), rookBa);
        pieces.put(rookBj.getCoords(), rookBj);

        pieces.put(knightBb.getCoords(), knightBb);
        pieces.put(knightBi.getCoords(), knightBi);

        pieces.put(bishopBc.getCoords(), bishopBc);
        pieces.put(bishopBh.getCoords(), bishopBh);

        pieces.put(GiraffeB.getCoords(), GiraffeB);
        pieces.put(VizarB.getCoords(), VizarB);
        pieces.put(WarCarBe.getCoords(), WarCarBe);
        pieces.put(WarCarBf.getCoords(), WarCarBf);

        pieces.put(queenB.getCoords(), queenB);

        pieces.put(kingB.getCoords(), kingB);


        pieces.put(pawnWa.getCoords(), pawnWa);
        pieces.put(pawnWb.getCoords(), pawnWb);
        pieces.put(pawnWc.getCoords(), pawnWc);
        pieces.put(pawnWd.getCoords(), pawnWd);
        pieces.put(pawnWe.getCoords(), pawnWe);
        pieces.put(pawnWf.getCoords(), pawnWf);
        pieces.put(pawnWg.getCoords(), pawnWg);
        pieces.put(pawnWh.getCoords(), pawnWh);
        pieces.put(pawnWi.getCoords(), pawnWi);
        pieces.put(pawnWj.getCoords(), pawnWj);

        pieces.put(rookWa.getCoords(), rookWa);
        pieces.put(rookWj.getCoords(), rookWj);

        pieces.put(knightWb.getCoords(), knightWb);
        pieces.put(knightWi.getCoords(), knightWi);

        pieces.put(bishopWc.getCoords(), bishopWc);
        pieces.put(bishopWh.getCoords(), bishopWh);

        pieces.put(GiraffeW.getCoords(), GiraffeW);
        pieces.put(VizarW.getCoords(), VizarW);
        pieces.put(WarCarWe.getCoords(), WarCarWe);
        pieces.put(WarCarWf.getCoords(), WarCarWf);

        pieces.put(queenW.getCoords(), queenW);

        pieces.put(kingW.getCoords(), kingW);

        return pieces;
    }
}
