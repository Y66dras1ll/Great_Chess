package com.chess.gui;

import com.chess.gui.controller.GameController;
import com.chess.gui.panels.BoardPanel;
import com.chess.gui.panels.InfoPanel;
import com.chess.engine.logic.Pieces;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(Pieces pieces) {
        setTitle("Великие шахматы");
        setBackground(Color.black);
        
        InfoPanel infoPanel = new InfoPanel();
        BoardPanel boardPanel = new BoardPanel(pieces);
        GameController gameController = new GameController(pieces, boardPanel, infoPanel);

        boardPanel.setGameController(gameController);
        infoPanel.setGameController(gameController);

        Container contents = getContentPane();
        contents.setLayout(new BorderLayout());
        contents.add(boardPanel, BorderLayout.WEST);
        contents.add(infoPanel, BorderLayout.EAST);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Pieces pieces = new Pieces();
        pieces.setGUIGame(true);
        new MainFrame(pieces);
    }
}