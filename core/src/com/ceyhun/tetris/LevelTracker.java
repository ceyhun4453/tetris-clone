package com.ceyhun.tetris;

public class LevelTracker {

    private static final int CLEARS_PER_LEVEL = 10;
    private int currentLevel = 0;
    private int clearedRows;
    private Command onLevelUp;

    public LevelTracker(int startingLevel) {
        this.currentLevel = startingLevel;
    }

    public void updateLevel(Clearer.Clear clear) {
        clearedRows += clear.getNumberOfLines();
        if (clearedRows > CLEARS_PER_LEVEL) {
            currentLevel++;
            onLevelUp.execute();
            clearedRows -= CLEARS_PER_LEVEL;
        }
    }

    public void setOnLevelUp(Command onLevelUp) {
        this.onLevelUp = onLevelUp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
