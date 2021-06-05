package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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

        clear.numberOfLines = clearFullRows(field, fullRows);
        clear.clearType = findClearType(field, lastMovement);
        if (previousClear != null && previousClear.clearType == clear.clearType
                && previousClear.numberOfLines == clear.numberOfLines && clear.numberOfLines != 0) {
            clear.streak = previousClear.streak + 1;
        } else if (clear.numberOfLines != 0) {
            clear.streak = 0;
        }

        if (clear.numberOfLines != 0) {
            previousClear = clear;
        }
        Gdx.app.log("CLEARER", "Type: " + clear.clearType + "\tStreak: " + clear.streak + "\t Lines: " + clear.numberOfLines);
        return clear;
    }

    private ClearType findClearType(Playfield field, Movement lastMovement) {
        if (field.getActivePiece().getTetrominoType() == Tetrimino.TetriminoType.T && (isRotation(lastMovement))) {
            //CHECK FOR T-SPIN
            //RETURN T-SPIN
            if (fullDiagonals(field) < 3) {
                return ClearType.Regular;
            }

        }

        return ClearType.Regular;
    }

    private int fullDiagonals(Playfield field) {
        int count = 0;
         for (int r = field.getActivePieceRow(); r <= field.getActivePieceRow() + 2; r += 2) {
             for (int c = field.getActivePieceCol(); c <= field.getActivePieceCol() + 2; c += 2) {
                 if (field.getValue(r, c) != Playfield.EMPTY_VALUE) {
                     count++;
                 }
             }
        }

         return count;
    }

    private int getCount(Playfield field, Side side, CellType cellType) {
        int count = 0;
        Map<RotationState, Vector2[]> source = T_FRONT_CORNERS;
        if (side.equals(Side.BACK)) {
            source = T_BACK_CORNERS;
        }

        for (Vector2 vector2 : source.get(field.getActivePiece().getRotationState())) {
            if (field.getCellType(field.getActivePieceRow() + (int) vector2.y,
                    field.getActivePieceCol() + (int) vector2.x) == cellType) {
                count++;
            }
        }

        return count;
    }

    private int occupiedCount(Playfield field, Side side) {

        return 0;
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
                if (field.getValue(row, col) == 0) {
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
}
