package com.mygdx.game;

import java.util.Arrays;

public class Playfield {

    private static final int WALL_VALUE = 9;
    private final int playFieldWidth = 16;
    private final int playFieldHeight = 32;
    private final int playAreaStartRow = 3;
    private final int playAreaEndRow = 26;
    private final int playAreaStartCol = 3;
    private final int playAreaEndCol = 12;
    private int newPieceVerticalPosition = 24;
    private final int newPieceHorizontalPosition = 6;
    // The time it takes for the active piece to move down 1 cell in ms.
    private final float fallTime = 0.75f;

    private WallKickData wallKickData;
    private Rotater rotater;
    // Arrangement of the play field. 0 means an unoccupied cell.
    private int[][] field;
    // The piece currently controlled by the player.
    private Tetrimino activePiece = null;
    // Index of the bottom-left corner of the active piece.
    private int activePieceRow = 0;
    private int activePieceCol = 0;

    public Playfield() {
        wallKickData = new WallKickData();
        field = new int[playFieldHeight][playFieldWidth];
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
        rotater = new Rotater();
    }

    private float counter = 0;

    private boolean speedUp = false;

    public void update(float deltaT) {
        if (activePiece == null) {
            checkForClears();
            addNewPiece();
        }

        if (counter >= fallTime) {
            for (int i = 0; i < (counter / fallTime) - 1; i++) {
                if (activePiece == null) {
                    break;
                }
                fall();
            }
            counter = 0;
        }

        if (speedUp) {
            counter += 20 * deltaT;
        } else {
            counter += deltaT;
        }
    }

    public void setSpeedUp(boolean speedUp) {
        this.speedUp = speedUp;
    }

    private void addNewPiece() {
        if (newPieceVerticalPosition > 26) {
            // GAMEOVER
            return;
        }
        activePiece = Tetrimino.getRandomTetromino();
        if (!isSpaceAvaiable(newPieceVerticalPosition, newPieceHorizontalPosition, activePiece)) {
            newPieceVerticalPosition++;
            addNewPiece();
        }
        activePieceRow = newPieceVerticalPosition;
        activePieceCol = newPieceHorizontalPosition;
        mergeActivePiece();
    }

    // Returns true if succesfully merged. Returns false if the cells were already occupied.
    private boolean mergeActivePiece() {
        int pieceLength = activePiece.getLength();
        for (int row = 0; row < pieceLength; row++) {
            for (int col = 0; col < pieceLength; col++) {
                if (activePiece.getValue(row, col) != 0) {
                    field[activePieceRow + row][activePieceCol + col] = activePiece.getValue(row, col);
                }
            }
        }
        return true;
    }

    public boolean isSpaceAvaiable(int row, int col, Tetrimino activePiece) {
        int pieceLength = activePiece.getLength();
        for (int r = 0; r < pieceLength; r++) {
            for (int c = 0; c < pieceLength; c++) {
                if (field[row + r][col + c] != 0 && activePiece.getValue(r, c) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fall() {
        if (!moveActivePiece(0, -1)) {
            activePiece = null;
        }
    }

    public boolean moveActivePiece(int x, int y) {
        boolean isMoved = false;
        if (activePiece != null) {
            unmergeActivePiece();
            if (isSpaceAvaiable(activePieceRow + y, activePieceCol + x, activePiece)) {
                activePieceCol += x;
                activePieceRow += y;
                isMoved = true;
            }
            mergeActivePiece();
        }
        return isMoved;
    }

    Rotation lastRotation = new Rotation(true);

    public void rotateActivePiece() {
//        if (activePiece != null) {
//            unmergeActivePiece();
//            RotationState lastRotationState = activePiece.getRotationState();
//            rotater.rotate(activePiece, Rotater.CLOCKWISE);
//            lastRotation.setStartState(lastRotationState);
//            lastRotation.setEndState(activePiece.getRotationState());
//            boolean isRotationPossible = false;
//            for (Vector2 v : wallKickData.getWallKickDataFor(lastRotation, activePiece)) {
//                int resultantRow = activePieceRow + Math.round(v.y);
//                int resultantCol = activePieceCol + Math.round(v.x);
//                if (isSpaceAvaiable(resultantRow, resultantCol, activePiece)) {
//                    activePieceRow = resultantRow;
//                    activePieceCol = resultantCol;
//                    isRotationPossible = true;
//                    break;
//                }
//            }
//            if (!isRotationPossible) {
//                rotater.rotate(activePiece, Rotater.ANTICLOCKWISE);
//            }
//            mergeActivePiece();
//        }
        unmergeActivePiece();
        rotater.rotatePiece(this, Rotater.CLOCKWISE);
        mergeActivePiece();
    }

    private void unmergeActivePiece() {
        int pieceLength = activePiece.getLength();
        for (int row = 0; row < pieceLength; row++) {
            for (int col = 0; col < pieceLength; col++) {
                if (activePiece.getValue(row, col) != 0) {
                    field[activePieceRow + row][activePieceCol + col] = 0;
                }
            }
        }
    }

    public int getPlayAreaValue(int row, int col) {
        return field[row + playAreaStartRow][col + playAreaStartCol];
    }

    public int getPlayAreaWidth() {
        return playAreaEndCol - playAreaStartCol + 1;
    }

    public int getPlayAreaHeight() {
        return playAreaEndRow - playAreaStartRow + 1;
    }

    public Tetrimino getActivePiece() {
        return activePiece;
    }

    public void checkForClears() {
        for (int row = playAreaEndRow; row >= playAreaStartRow; row--) {
            if (isRowFull(row)) {
                clearRow(row);
                moveAllRowsAboveDown(row);
            }
        }
    }

    int[] temp = new int[playAreaEndCol - playAreaStartCol + 1];

    public void moveAllRowsAboveDown(int row) {
        for (int r = row + 1; r <= playAreaEndRow; r++) {
            for (int c = playAreaStartCol; c <= playAreaEndCol; c++) {
                field[r - 1][c] = field[r][c];
            }

        }
    }

    public void clearRow(int row) {
        for (int col = playAreaStartCol; col <= playAreaEndCol; col++) {
            field[row][col] = 0;
        }
    }

    public boolean isRowFull(int row) {
        for (int col = playAreaStartCol; col <= playAreaEndCol; col++) {
            if (field[row][col] == 0) {
                return false;
            }
        }

        return true;
    }
}
