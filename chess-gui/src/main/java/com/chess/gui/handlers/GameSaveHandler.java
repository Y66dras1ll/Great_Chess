package com.chess.gui.handlers;


import com.chess.engine.enums.COLOUR;
import com.chess.engine.logic.Coordinate;
import com.chess.engine.notation.ChessIO;
import com.chess.engine.pieces.King;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;

public class GameSaveHandler {
    private static final Color INFO_COLOR = new Color(51,51,51);

    public static void handleSaveGame(String moveHistory) {
        ImageIcon icon = com.chess.gui.utils.UploadFigureUtils.getIcon(
                new King(COLOUR.W, new Coordinate('f', 1)));

        UIManager.put("OptionPane.background", INFO_COLOR);
        UIManager.put("Panel.background", INFO_COLOR);
        UIManager.put("OptionPane.messageForeground", Color.white);

        String fileSave = (String) JOptionPane.showInputDialog(null,
                "Введите имя файла, чтобы сохранить игру:",
                "Сохранить игру",
                JOptionPane.INFORMATION_MESSAGE,
                icon,null,null);

        if (fileSave != null) {
            String filePath = ChessIO.toTxt(fileSave);

            if (ChessIO.isErrorSave(filePath)) {
                JOptionPane.showMessageDialog(null,
                        fileSave + " не подходит для файла.",
                        "Ошибка сохранения.",
                        JOptionPane.ERROR_MESSAGE,
                        icon);
            } else {
                if (ChessIO.saveGame(moveHistory, Paths.get(filePath)))
                    JOptionPane.showMessageDialog(null,
                            "Игра была сохранена на файл " + filePath,
                            "Успешное сохранение.",
                            JOptionPane.INFORMATION_MESSAGE,
                            icon);
                else
                    JOptionPane.showMessageDialog(null,
                            "Произошла ошибка при сохранении игры на файл" + filePath + ". Убедитесь, что файл не существует.",
                            "Ошибка сохранения.",
                            JOptionPane.ERROR_MESSAGE,
                            icon);
            }
        }
    }
}