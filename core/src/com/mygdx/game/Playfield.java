package com.mygdx.game;

import java.util.Arrays;

public class Playfield {
    public static final int WALL_VALUE = 9;
    public static final int EMPTY_VALUE = 0;
    public static final int GHOST = 143;
    private final int playFieldWidth = 16;
    private final int playFieldHeight = 48;
    private final int playAreaStartRow = 3;
    private final int playAreaEndRow = 42;
    private final int playAreaStartCol = 3;
    private final int playAreaEndCol = 12;
    private final int[][] field;
    private int newPieceRow = 21;
    private int newPieceCol = 4;
    private Tetrimino activePiece;
    private int activePieceRow;
    private int activePieceCol;
    private int ghostRow;
    private int ghostCol;
    // This flag is used to prevent repeated calls of unmerge or merge methods so that cfield[playAreaRowToRow(row) + r][playAreaColToCol(col) + c] != EMPTY_VALUEallers can use merge and unmerge
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
                Arrays.fill(array, EMPTY_VALUE);
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
                    if (activePiece.getValue(r, c) != EMPTY_VALUE) {
                        field[activePieceRow + r][activePieceCol + c] = EMPTY_VALUE;
                    }
                }
            }
            isMerged = false;
        }
    }

    // Gets the exact value stored in the playfield. Callers should use getType()
    // unless they really need the exact value to function.
    public int getValue(int row, int col) {
        return field[playAreaRowToRow(row)][playAreaColToCol(col)];
    }

    private CellType toCellType(int value) {
        if (value == WALL_VALUE) {
            return CellType.WALL;
        } else if (value == EMPTY_VALUE || value == GHOST) {
            return CellType.EMPTY;
        }

        return CellType.TETRIMINO;
    }

    // Gets the type of the cell.
    public CellType getCellType(int row, int col) {
        return toCellType(getValue(row, col));
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

    public boolean isSpaceAvailable(int row, int col, Tetrimino piece) {
        int pieceLength = piece.getLength();
        for (int r = 0; r < pieceLength; r++) {
            for (int c = 0; c < pieceLength; c++) {
                if (toCellType(field[playAreaRowToRow(row) + r][playAreaColToCol(col) + c]) != CellType.EMPTY
                        && toCellType(piece.getValue(r, c)) != CellType.EMPTY) {
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
        // To make sure ghost piece is immediately removed when the active piece is changed.
        if (this.activePiece != null) {
            unmergeGhost();
        }
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

    public void updateGhost(int row, int col) {
        unmergeGhost();
        ghostRow = playAreaRowToRow(row);
        ghostCol = playAreaColToCol(col);
        mergeGhost();
    }

    private void unmergeGhost() {
        int length = activePiece.getLength();
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                if (toCellType(field[ghostRow + r][ghostCol + c]) == CellType.EMPTY) {
                    field[ghostRow + r][ghostCol + c] = EMPTY_VALUE;
                }
            }
        }
    }

    private void mergeGhost() {
        int length = activePiece.getLength();
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                if (activePiece.getValue(r, c) != EMPTY_VALUE) {
                    field[ghostRow + r][ghostCol + c] = GHOST;
                }
            }
        }
    }
}
