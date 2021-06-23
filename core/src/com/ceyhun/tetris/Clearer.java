package com.ceyhun.tetris;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;

import java.util.HashMap;
import java.util.Map;

public class Clearer {

    private static final Map<RotationState, Vector2[]> T_FRONT_CORNERS;
    private static final Map<RotationState, Vector2[]> T_BACK_CORNERS;

    static {
        T_FRONT_CORNERS = new HashMap<>();
        T_BACK_CORNERS = new HashMap<>();

        T_FRONT_CORNERS.put(RotationState.SPAWN, new Vector2[]{new Vector2(0, 2), new Vector2(2, 2)});
        T_BACK_CORNERS.put(RotationState.SPAWN, new Vector2[]{new Vector2(0, 0), new Vector2(2, 0)});
        T_FRONT_CORNERS.put(RotationState.CLOCKWISE90, new Vector2[]{new Vector2(2, 2), new Vector2(2, 0)});
        T_BACK_CORNERS.put(RotationState.CLOCKWISE90, new Vector2[]{new Vector2(0, 0), new Vector2(0, 2)});
        T_FRONT_CORNERS.put(RotationState.CLOCKWISE180, new Vector2[]{new Vector2(0, 0), new Vector2(2, 0)});
        T_BACK_CORNERS.put(RotationState.CLOCKWISE180, new Vector2[]{new Vector2(0, 2), new Vector2(2, 2)});
        T_FRONT_CORNERS.put(RotationState.CLOCKWISE270, new Vector2[]{new Vector2(0, 0), new Vector2(0, 2)});
        T_BACK_CORNERS.put(RotationState.CLOCKWISE270, new Vector2[]{new Vector2(2, 2), new Vector2(2, 0)});
    }

    private Clear previousClear;

    // Returns a list of cleared rows.
    public Clear clearFullRows(Playfield field, Movement lastMovement) {
        Clear clear = new Clear(ClearType.Regular, 0, 0);
        IntArray fullRows = checkForFullRows(field);
        if (fullRows.isEmpty()) {
            return clear;
        }

        clear.clearType = findClearType(field, lastMovement);
        clear.numberOfLines = clearFullRows(field, fullRows);
        if (previousClear != null && previousClear.clearType == clear.clearType
                && previousClear.numberOfLines == clear.numberOfLines && clear.numberOfLines != 0) {
            clear.streak = previousClear.streak + 1;
        } else if (clear.numberOfLines != 0) {
            clear.streak = 0;
        }

        if (clear.numberOfLines != 0) {
            previousClear = clear;
        }
        return clear;
    }

    private ClearType findClearType(Playfield field, Movement lastMovement) {
        if (field.getActivePiece().getTetrominoType() == Tetrimino.TetriminoType.T && (isRotation(lastMovement))) {
            if (fullDiagonals(field) >= 3) {
                if (getMinoCount(field, Side.FRONT) == 2 && getMinoCount(field, Side.BACK) >= 1) {
                    return ClearType.TSpin;
                } else if (isSpecialWallKick(lastMovement)) {
                    return ClearType.TSpin;
                } else {
                    return ClearType.MiniTSpin;
                }
            }
        }
        return ClearType.Regular;
    }

    private boolean isSpecialWallKick(Movement movement) {
        return movement.getNewPosition().x - movement.getOldPosition().x == 1 &&
                movement.getNewPosition().y - movement.getOldPosition().y == -2;
    }

    private int fullDiagonals(Playfield field) {
        return getOccupiedCount(field, Side.BACK) + getOccupiedCount(field, Side.FRONT);
    }

    // Returns the count of cells accepted (returns true when passed as argument) CellChecker on the
    // front or back corners of the active piece in the field.
    private int getCount(Playfield field, Side side, CellChecker checker) {
        int count = 0;
        Map<RotationState, Vector2[]> source = T_FRONT_CORNERS;
        if (side.equals(Side.BACK)) {
            source = T_BACK_CORNERS;
        }
        for (Vector2 vector2 : source.get(field.getActivePiece().getRotationState())) {
            if (checker.check(field.getCellType(field.getActivePieceRow() + (int) vector2.y,
                    field.getActivePieceCol() + (int) vector2.x))) {
                count++;
            }
        }

        return count;
    }

    // Returns the number of corners occupied by tetriminos on the corners of the given side.
    private int getMinoCount(Playfield field, Side side) {
        return getCount(field, side, cellType -> cellType.equals(CellType.TETRIMINO));
    }

    // Returns the number of occuped cells (not empty) on the corners of the given side.
    private int getOccupiedCount(Playfield field, Side side) {
        return getCount(field, side, cellType -> !cellType.equals(CellType.EMPTY));
    }

    private boolean isRotation(Movement movement) {
        return !movement.getOldRotationState().equals(movement.getNewRotationState());
    }

    private int clearFullRows(Playfield field, IntArray fullRows) {
        int clearCount = 0;
        fullRows.sort();
        for (int i = fullRows.size - 1; i >= 0; i--) {
            clearRow(field ,fullRows.get(i));
            moveAllRowsAboveDown(field, fullRows.get(i));
            clearCount++;
        }
        return clearCount;
    }

    //Returns the numbers of the full rows.
    private IntArray checkForFullRows(Playfield field) {
        IntArray fullRows = new IntArray();
        for (int r = field.getPlayAreaHeight() -1; r>=0; r--) {
            if(isRowFull(field, r)) {
                fullRows.add(r);
            }
        }

        return fullRows;
    }
    
    private void moveAllRowsAboveDown(Playfield field, int row) {
        for (int r = row + 1; r < field.getPlayAreaHeight(); r++) {
            for (int c = 0; c < field.getPlayAreaWidth(); c++) {
                field.setValue(field.getValue(r, c), r - 1, c);
            }

        }
    }

    private boolean isRowFull(Playfield field, int row) {
            for (int col = 0; col < field.getPlayAreaWidth(); col++) {
                if (field.getCellType(row, col).equals(CellType.EMPTY)) {
                    return false;
                }
            }

            return true;
    }

    private void clearRow(Playfield field, int row) {
        for (int col = 0; col < field.getPlayAreaWidth(); col++) {
            field.setValue(0, row, col);
        }
    }

    public enum ClearType {
        Regular, MiniTSpin, TSpin;
    }

    public static class Clear {
        private ClearType clearType;
        private int numberOfLines;
        private int streak;

        public Clear(ClearType clearType, int numberOfLines, int streak) {
            this.clearType = clearType;
            this.numberOfLines = numberOfLines;
            this.streak = streak;
        }

        public ClearType getClearType() {
            return clearType;
        }

        public int getNumberOfLines() {
            return numberOfLines;
        }

        public int getStreak() {
            return streak;
        }
    }

    private static enum Side {
        BACK, FRONT;
    }

    private interface CellChecker {
        boolean check(CellType cellType);
    }
}
