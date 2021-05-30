package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Movement {
    private Vector2 oldPosition;
    private Vector2 newPosition;
    private RotationState oldRotationState;
    private RotationState newRotationState;

    public Movement(Vector2 oldPosition, Vector2 newPosition, RotationState oldRotationState, RotationState newRotationState) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.oldRotationState = oldRotationState;
        this.newRotationState = newRotationState;
    }

    public Vector2 getOldPosition() {
        return oldPosition;
    }

    public Vector2 getNewPosition() {
        return newPosition;
    }

    public RotationState getOldRotationState() {
        return oldRotationState;
    }

    public RotationState getNewRotationState() {
        return newRotationState;
    }

    @Override
    public String toString() {
        return "Old Position: " + oldPosition + " New Position: " + newPosition
                + " Old Rotation: " + oldRotationState + " New Rotation: " + newRotationState;
    }
}
