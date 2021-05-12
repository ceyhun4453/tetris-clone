package com.mygdx.game;

import java.util.Arrays;

public class Playfield {
    private static final int WALL_VALUE = 9;
    private final int playFieldWidth = 16;
    private final int playFieldHeight = 48;
    private final int playAreaStartRow = 3;
    private final int playAreaEndRow = 42;
    private final int playAreaStartCol = 3;
    private final int playAreaEndCol = 12;
    private final int[][] field;
    private Tetrimino activePiece;
    private int activePieceRow;
    private int activePieceCol;
    // This flag is used to prevent repeated calls of unmerge or merge methods so that callers can use merge and unmerge
    // without worrying about causing weird bugs.
    private boolean isMerged = false;

    public Playfield() {
        field = new int[playFieldHeight][playFieldWidth];
        setUpField();
    }

    private void setUpField() {
        for (int i = 0; i < field.length; i++) {
            int[] array = field[i];
            if (i < playAreaStartRow) {
                Arrays.fill(array, WALL_VALUE);
            } else {
                Arrays.fill(array, 0);
                for (int j = 0; j < playAreaStartCol; j++) {
                    array[j] = WALL_VALUE;
                    array[array.length - (j + 1)] = WALL_VALUE;
                }
            }
        }
    }

    public void mergeActivePiece() {
        if (!isMerged && activePiece != null) {
            int pieceLength = activePiece.getLength();
            for (int r = 0; r < pieceLength; r++) {
                for (int c = 0; c < pieceLength; c++) {
                    if (activePiece.getValue(r, c) != 0) {
                        field[activePieceRow + r][activePieceCol + c] = activePiece.getValue(r, c);
                    }
                }
            }
            isMerged = true;
        }
    }

    public void unmergeActivePiece() {
        if (isMerged && activePiece != null) {
            int pieceLength = activePiece.getLength();
            for (int r = 0; r < pieceLength; r++) {
                for (int c = 0; c < pieceLength; c++) {
                    if (activePiece.getValue(r, c) != 0) {
                        field[activePieceRow + r][activePieceCol + c] = 0;
                    }
                }
            }
            isMerged = false;
        }
    }

    public int getValue(int row, int col) {
        return field[playAreaRowToRow(row)][playAreaColToCol(col)];
    }

    public void setValue(int value, int row, int col) {
        field[playAreaRowToRow(row)][playAreaColToCol(col)] = value;
    }

    public int getPlayAreaWidth() {
        return playAreaEndCol - playAreaStartCol + 1;
    }

    public int getPlayAreaHeight() {
        return playAreaEndRow - playAreaStartRow + 1;
    }

    public boolean isSpaceAvaiable(int row, int col, Tetrimino piece) {
        int pieceLength = piece.getLength();
        for (int r = 0; r < pieceLength; r++) {
            for (int c = 0; c < pieceLength; c++) {
                if (field[playAreaRowToRow(row) + r][playAreaColToCol(col) + c] != 0 && piece.getValue(r, c) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int playAreaRowToRow(int row) {
        return row + playAreaStartRow;
    }

    private int playAreaColToCol(int col) {
        return col + playAreaStartCol;
    }

    private int rowToPlayAreaRow(int row) {
        return row - playAreaStartRow;
    }

    private int colToPlayAreaCol(int col) {
        return col - playAreaStartCol;
    }

    public void setActivePiece(Tetrimino activePiece) {
        this.activePiece = activePiece;
    }

    public void setActivePieceRow(int activePieceRow) {
        this.activePieceRow = playAreaRowToRow(activePieceRow);
    }

    public void setActivePieceCol(int activePieceCol) {
        this.activePieceCol = playAreaColToCol(activePieceCol);
    }

    public Tetrimino getActivePiece() {
        return activePiece;
    }

    public int getActivePieceRow() {
        return rowToPlayAreaRow(activePieceRow);
    }

    public int getActivePieceCol() {
        return colToPlayAreaCol(activePieceCol);
    }
}
