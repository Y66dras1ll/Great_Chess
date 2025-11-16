package com.chess.gui.panels;

import com.chess.engine.enums.BOARD;
import com.chess.gui.controller.GameController;
import com.chess.engine.logic.Coordinate;
import com.chess.engine.logic.Pieces;
import com.chess.engine.pieces.Piece;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class BoardPanel extends JPanel {
    private final int dimension = BOARD.LAST_RANK.getRankVal();
    private final int firstRank = BOARD.FIRST_RANK.getRankVal();
    private final char firstFile = BOARD.FIRST_FILE.getFileVal();
    private final char lastFile = BOARD.LAST_FILE.getFileVal();
    private final char charFile = (char) (firstFile - 1);

    private final JButton[][] board = new JButton[dimension][dimension];
    private final Color brown = new Color(150, 75, 0);
    private final Color pastel = new Color(255, 222, 173);
    private final Color intermediate = new Color(255, 255, 153);
    private static final int tileSize = 88;

    private final BufferedImage invisible = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
    private final ImageIcon invisibleIcon = new ImageIcon(invisible);

    private GameController gameController;
    private boolean isBoardEnabled = true;

    public BoardPanel(Pieces pieces) {
        setLayout(new BorderLayout());
        initializeBoard(pieces);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void initializeBoard(Pieces pieces) {
        JPanel boardPanel = new JPanel(new GridLayout(dimension, dimension));

        for (int rank = dimension; rank >= firstRank; rank--) {
            for (int file = 1; file <= dimension; file++) {
                char fileChar = (char) (charFile + file);
                Coordinate tileCoord = new Coordinate(fileChar, rank);
                board[rank - 1][file - 1] = createBoardButton(tileCoord, pieces);
                board[rank - 1][file - 1].addActionListener(new BoardButtonListener());
                boardPanel.add(board[rank - 1][file - 1]);
            }
        }

        add(createFileLabelsTop(), BorderLayout.NORTH);
        add(createRankLabelsLeft(), BorderLayout.WEST);
        add(boardPanel, BorderLayout.CENTER);
        add(createRankLabelsRight(), BorderLayout.EAST);
        add(createFileLabelsBottom(), BorderLayout.SOUTH);
    }

    private JButton createBoardButton(Coordinate coordinate, Pieces pieces) {
        JButton button = new JButton();
        backgroundSetter(coordinate, button);

        Piece piece = pieces.getPieces().get(coordinate);
        if (piece != null) {
            ImageIcon icon = com.chess.gui.utils.UploadFigureUtils.getIcon(piece);
            button.setIcon(icon);
            // Сохраняем оригинальную иконку для использования при отключении
            button.setDisabledIcon(icon);
        } else {
            button.setIcon(invisibleIcon);
            button.setDisabledIcon(invisibleIcon);
        }

        formatButton(button);
        return button;
    }

    private void backgroundSetter(Coordinate coordinate, JButton button) {
        int signature = coordinate.getFile() - charFile + coordinate.getRank();
        if (signature % 2 == 0) {
            button.setBackground(brown);
        } else {
            button.setBackground(pastel);
        }
    }

    private void formatButton(JButton button) {
        button.setSize(tileSize, tileSize);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setVisible(true);
    }

    public void highlightPossibleMoves(HashSet<Coordinate> potentials) {
        if (!isBoardEnabled) return;

        for (int rank = 1; rank <= dimension; rank++) {
            for (char file = firstFile; file <= lastFile; file++) {
                int processedRank = rank - firstRank;
                int processedFile = file - firstFile;
                Coordinate potentialCoord = new Coordinate(file, rank);
                if (potentials.contains(potentialCoord))
                    board[processedRank][processedFile].setBackground(intermediate);
                else
                    backgroundSetter(potentialCoord, board[processedRank][processedFile]);
            }
        }
    }

    public void resetBoardColors() {
        for (int rank = 1; rank <= dimension; rank++) {
            for (char file = firstFile; file <= lastFile; file++) {
                int processedRank = rank - firstRank;
                int processedFile = file - firstFile;
                Coordinate potentialCoord = new Coordinate(file, rank);
                backgroundSetter(potentialCoord, board[processedRank][processedFile]);
            }
        }
    }

    public void updateBoard(Pieces pieces) {
        for (int rank = 1; rank <= dimension; rank++) {
            for (char file = firstFile; file <= lastFile; file++) {
                int processedRank = rank - firstRank;
                int processedFile = file - firstFile;
                Coordinate potentialCoord = new Coordinate(file, rank);
                backgroundSetter(potentialCoord, board[processedRank][processedFile]);

                Piece updatePiece = pieces.getPieces().get(potentialCoord);
                if (updatePiece != null) {
                    ImageIcon icon = com.chess.gui.utils.UploadFigureUtils.getIcon(updatePiece);
                    board[processedRank][processedFile].setIcon(icon);
                    // Обновляем иконку для отключенного состояния
                    board[processedRank][processedFile].setDisabledIcon(icon);
                } else {
                    board[processedRank][processedFile].setIcon(invisibleIcon);
                    board[processedRank][processedFile].setDisabledIcon(invisibleIcon);
                }
            }
        }
    }

    public void disableBoard() {
        isBoardEnabled = false;
        for (int row = 0; row < dimension; row++) {
            for (int file = 0; file < dimension; file++) {
                // Вместо setEnabled(false) просто удаляем обработчики событий
                // Это предотвратит потускнение иконок
                ActionListener[] listeners = board[row][file].getActionListeners();
                for (ActionListener listener : listeners) {
                    board[row][file].removeActionListener(listener);
                }
            }
        }
    }

    // Методы для создания меток файлов и рангов остаются без изменений
    private JPanel createRanks() {
        JPanel ranks = new JPanel(new GridLayout(dimension, 0));
        ranks.setBackground(pastel);
        int rankPad = 4;
        for (int rank = dimension; rank >= firstRank; rank--) {
            JLabel rankLabel = new JLabel(String.valueOf(rank));
            rankLabel.setFont(new Font("TimesRoman", Font.BOLD, 23));
            rankLabel.setForeground(brown);
            rankLabel.setBorder(new EmptyBorder(0, rankPad, 0, rankPad));
            ranks.add(rankLabel);
        }
        return ranks;
    }

    private JPanel createFiles() {
        JPanel files = new JPanel(new GridLayout(0, dimension));
        files.setBackground(pastel);
        int filePad = 42;
        for (char file = firstFile; file <= lastFile; file++) {
            JLabel fileLabel = new JLabel(String.valueOf(file));
            fileLabel.setFont(new Font("TimesRoman", Font.BOLD, 23));
            fileLabel.setForeground(brown);
            fileLabel.setBorder(new EmptyBorder(0, filePad, 0, filePad));
            files.add(fileLabel);
        }
        return files;
    }

    private JPanel createBorder() {
        JPanel border = new JPanel();
        border.setBackground(brown);
        return border;
    }

    private Border createCorner() {
        int borderPad = 20;
        return new MatteBorder(0, borderPad, 0, borderPad, pastel);
    }

    private JPanel createRankLabelsLeft() {
        JPanel rankLabels = new JPanel(new BorderLayout());
        rankLabels.add(createRanks(), BorderLayout.WEST);
        rankLabels.add(createBorder(), BorderLayout.EAST);
        return rankLabels;
    }

    private JPanel createFileLabelsBottom() {
        JPanel fileLabels = new JPanel(new BorderLayout());
        fileLabels.add(createFiles(), BorderLayout.SOUTH);
        fileLabels.add(createBorder(), BorderLayout.NORTH);
        fileLabels.setBorder(createCorner());
        return fileLabels;
    }

    private JPanel createRankLabelsRight() {
        JPanel rankLabels = new JPanel(new BorderLayout());
        rankLabels.add(createRanks(), BorderLayout.EAST);
        rankLabels.add(createBorder(), BorderLayout.WEST);
        return rankLabels;
    }

    private JPanel createFileLabelsTop() {
        JPanel fileLabels = new JPanel(new BorderLayout());
        fileLabels.add(createFiles(), BorderLayout.NORTH);
        fileLabels.add(createBorder(), BorderLayout.SOUTH);
        fileLabels.setBorder(createCorner());
        return fileLabels;
    }

    private class BoardButtonListener implements java.awt.event.ActionListener {
        private Coordinate toCoordinate(int row, int column) {
            int rank = row + firstRank;
            char file = (char) (column + firstFile);
            return new Coordinate(file, rank);
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            if (!isBoardEnabled || gameController == null) return;

            Coordinate coordinate = null;
            JButton source = (JButton) actionEvent.getSource();

            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (board[i][j].equals(source)) {
                        coordinate = toCoordinate(i, j);
                    }
                }
            }

            if (coordinate != null && Coordinate.inBoard(coordinate)) {
                gameController.handleTileClick(coordinate);
            }
        }
    }
}