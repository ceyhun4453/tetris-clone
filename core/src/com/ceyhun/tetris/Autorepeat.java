package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import javax.swing.*;

public class Autorepeat {

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    private static final float START_DELAY = 0.1f;
    private static final float STEP = 0.1f;

    private float counter;
    private float startDelayCounter;
    private final Translater translater;
    private boolean isOn;
    private boolean started;
    private final Vector2 direction;
    private int currentDirectionNumber;

    public Autorepeat(Translater translater) {
        this.translater = translater;
        isOn = false;
        counter = 0;
        startDelayCounter = 0;
        direction = new Vector2();
        currentDirectionNumber = 0;
    }

    public void stopAutorepeat(int direction) {
        if (this.direction.equals(getDirectionVector(direction))) {
            isOn = false;
            started = false;
            counter = 0;
            startDelayCounter = 0;
        }
    }

    public boolean isOn() {
        return isOn;
    }

    private Vector2 getDirectionVector(int dirNumber) {
        Vector2 direction = new Vector2();
        switch (dirNumber) {
            case UP:
                direction.set(0, 1);
                break;
            case DOWN:
                direction.set(0, -1);
                break;
            case LEFT:
                direction.set(-1, 0);
                break;
            case RIGHT:
                direction.set(1, 0);
        }

        return direction;
    }

    public void stepAutorepeat(int direction, Playfield field) {
        float deltaT = Gdx.graphics.getDeltaTime();
        if (!isOn) {
            isOn = true;
            this.direction.set(getDirectionVector(direction));
            currentDirectionNumber = direction;
            return;
        }

        if (direction == currentDirectionNumber) {
            if (!started) {
                startDelayCounter += deltaT;
                if (startDelayCounter > START_DELAY) {
                    counter = 0;
                    started = true;
                }
                return;
            }

            counter += deltaT;
            if (counter > STEP) {
                translater.movePiece(field, Math.round(this.direction.x), Math.round(this.direction.y));
                counter = 0;
            }
        }

    }

}
