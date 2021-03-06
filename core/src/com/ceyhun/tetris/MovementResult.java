package com.ceyhun.tetris;

import com.badlogic.gdx.math.Vector2;

public interface MovementResult {

    boolean isSuccessful();
    MovementType movementType();

    enum MovementType {
        LinearMovement, RotationalMovement;
    }
}
