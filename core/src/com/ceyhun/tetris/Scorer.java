package com.ceyhun.tetris;

public class Scorer {
    private static final int SOFTDROP_SCORE_MODIFIER = 1;
    private static final int HARDDROP_SCORE_MODIFIER = 2;

    private Clearer.Clear lastClear;
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

    public void updateScoreWithClear(Clearer.Clear clear, int level) {
        if (clear.getClearType() == Clearer.ClearType.Regular) {
            switch (clear.getNumberOfLines()) {
                case 1:
                    score += 100 * level;
                    break;
                case 2:
                    score += 300 * level;
                    break;
                case 3:
                    score += 500 * level;
                    break;
                case 4:
                    if (clear.getStreak() > 0) {
                        score += 1200 * level;
                        break;
                    }
                    score += 800;
                    break;
            }
        } else if (clear.getClearType() == Clearer.ClearType.MiniTSpin) {
            score += 200 * level;
        } else if (clear.getClearType() == Clearer.ClearType.TSpin) {
            switch (clear.getNumberOfLines()) {
                case 1:
                    if (clear.getStreak() > 0) {
                        score += 1200 * level;
                        break;
                    }
                    score += 800 * level;
                    break;
                case 2:
                    if (clear.getStreak() > 0) {
                        score += 1600 * level;
                        break;
                    }
                    score = 1200 * level;
            }
        }

        if (clear.getNumberOfLines() != 0) {
            lastClear = clear;
        }
    }

    public int getScore() {
        return score;
    }
}
