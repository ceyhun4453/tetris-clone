package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Scorer {
    private static final int SOFTDROP_SCORE_MODIFIER = 1;
    private static final int HARDDROP_SCORE_MODIFIER = 2;

    private int lastClearSize = 0;
    private int score = 0;

    public Scorer() {

    }

    public void updateScoreWithGravity(Gravity.GravityEvent gravityEvent) {
        if (gravityEvent.getGravityState() == Gravity.GravityState.SoftDrop) {
            score += SOFTDROP_SCORE_MODIFIER * gravityEvent.getNumberOfCellsMoved();
        } else if (gravityEvent.getGravityState() == Gravity.GravityState.HardDrop) {
            score += HARDDROP_SCORE_MODIFIER * gravityEvent.getNumberOfCellsMoved();
        }
    }

    public void updateScoreWithClear(int numberOfClearedRows, int level) {
        switch (numberOfClearedRows) {
            case 0:
                lastClearSize = 0;
                break;
            case 1:
                score += level * 100;
                lastClearSize = 1;
                break;
            case 2:
                score += level * 300;
                lastClearSize = 2;
                break;
            case 3:
                score += level * 500;
                lastClearSize = 3;
                break;
            case 4:
                if (lastClearSize == 4) {
                    score += 1200 * level;
                } else {
                    score += 800 * level;
                }
                lastClearSize = 4;
                break;
        }
    }

    public int getScore() {
        return score;
    }
}
