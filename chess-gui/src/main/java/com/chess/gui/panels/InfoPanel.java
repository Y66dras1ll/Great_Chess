package com.chess.gui.panels;


import com.chess.engine.enums.COLOUR;
import com.chess.gui.controller.GameController;
import com.chess.gui.manager.MoveHistoryManager;
import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Pieces;
import com.chess.engine.pieces.Piece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoPanel extends JPanel {
    private final JTextPane movePane = new JTextPane();
    private final JTextPane matePane = new JTextPane();
    private final JButton saveButton = new JButton("Сохранить игру");

    private final Color infoColour = new Color(51,51,51);
    private final MoveHistoryManager moveHistoryManager;

    private GameController gameController;

    public InfoPanel() {
        this.moveHistoryManager = new MoveHistoryManager();
        initializePanel();
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void initializePanel() {
        setLayout(new GridBagLayout());
        setBackground(infoColour);
        setPreferredSize(new Dimension(300, 800));

        GridBagConstraints gbc = new GridBagConstraints();

        // Настройка movePane
        movePane.setEditable(false);
        movePane.setForeground(Color.white);
        movePane.setBackground(infoColour);
        movePane.setFont(new Font("Arial", Font.BOLD, 14));
        movePane.setBorder(new EmptyBorder(40, 20, 40, 20));
        JScrollPane scrollMoves = new JScrollPane(movePane);

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.4;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollMoves, gbc);

        // Настройка кнопки сохранения
        saveButton.setBackground(new Color(128,128,128));
        saveButton.setForeground(Color.white);
        saveButton.setOpaque(true);
        saveButton.setContentAreaFilled(true);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(e -> {
            if (gameController != null) {
                gameController.saveGame();
            }
        });

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.NONE;
        add(saveButton, gbc);


        matePane.setEditable(false);
        matePane.setForeground(Color.white);
        matePane.setBackground(infoColour);
        matePane.setFont(new Font("Arial", Font.BOLD, 20));
        matePane.setBorder(new EmptyBorder(0, 80, 40, 80));

        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(matePane, gbc);
    }

    public void recordMove(Coordinate coordinate, Piece piece, Pieces pieces, COLOUR turn) {
        moveHistoryManager.recordMove(coordinate, piece, pieces, turn);
        movePane.setText(moveHistoryManager.getFullMoveHistory());
    }

    public void setGameResult(String result) {
        matePane.setText(result);
    }

    public String getMoveHistory() {
        return moveHistoryManager.getFullMoveHistory();
    }
}