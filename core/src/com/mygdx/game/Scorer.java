package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Scorer {

    private int lastClearSize = 0;
    private int score = 0;

    public Scorer() {

    }

    public void updateScore(int numberOfClearedRows, int level) {
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
        Gdx.app.log("Score: ", String.valueOf(score));
    }
}