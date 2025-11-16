package com.chess.gui.handlers;


import com.chess.engine.logic.Coordinate;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Queen;

import javax.swing.*;
import java.awt.*;

public class PawnPromotionHandler {
    private static final Color INFO_COLOR = new Color(51,51,51);

    public static void handlePawnPromotion(Pawn pawn, Coordinate promotionSquare) {
        ImageIcon icon = com.chess.gui.utils.UploadFigureUtils.getIcon(new Queen(pawn.getColour(), promotionSquare));

        UIManager.put("OptionPane.background", INFO_COLOR);
        UIManager.put("Panel.background", INFO_COLOR);
        UIManager.put("OptionPane.messageForeground", Color.white);

        JOptionPane.showOptionDialog(
                null,
                "Ваша пешка стала ферзём.",
                "Повышение ранга",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                null,
                null
        );

        pawn.setPromotedPiece(new Queen(pawn.getColour(), promotionSquare));
    }
}