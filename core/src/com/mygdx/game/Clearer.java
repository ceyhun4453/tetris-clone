package com.mygdx.game;

import com.badlogic.gdx.utils.IntArray;

public class Clearer {

    private final IntArray clearedRows = new IntArray(4);

    // Returns a list of cleared rows.
    public IntArray clearFullRows(Playfield field) {
        clearedRows.clear();
        for (int r = field.getPlayAreaHeight() - 1; r >= 0; r--) {
            if(isRowFull(field, r)) {
                clearRow(field, r);
                moveAllRowsAboveDown(field, r);
                clearedRows.add(r);
            }
        }

        return clearedRows;
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
}
