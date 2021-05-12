package com.mygdx.game;

public interface MovementResult {

    boolean isSuccessful();
    MovementType getMovementType();

    enum MovementType {
        LinearMovement, RotationalMovement;
    }
}
