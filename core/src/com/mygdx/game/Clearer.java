package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntArray;

public class Clearer {

    private Clear previousClear;

    // Returns a list of cleared rows.
    public Clear clearFullRows(Playfield field) {
        Clear clear = new Clear();
        clear.numberOfLines = 0;
        for (int r = field.getPlayAreaHeight() - 1; r >= 0; r--) {
            if(isRowFull(field, r)) {
                clearRow(field, r);
                moveAllRowsAboveDown(field, r);
                clear.numberOfLines += 1;
            }
        }
        clear.clearType = ClearType.Regular;
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

        private Clear() {
        }

        private void setClearType(ClearType clearType) {
            this.clearType = clearType;
        }

        private void setNumberOfLines(int numberOfLines) {
            this.numberOfLines = numberOfLines;
        }

        private void setStreak(int streak) {
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
}
