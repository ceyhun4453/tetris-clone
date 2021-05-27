package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Translation {
    private Vector2 oldPosition;
    private Vector2 newPosition;

    public Translation(Vector2 oldPosition, Vector2 newPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public Vector2 getOldPosition() {
        return oldPosition;
    }

    public Vector2 getNewPosition() {
        return newPosition;
    }
}
