package com.mygdx.game;

public enum RotationState {
    SPAWN, CLOCKWISE90, CLOCKWISE180, CLOCKWISE270;

    public static RotationState getClockwiseRotationState(RotationState currentState) {
        if (currentState.ordinal() == values().length - 1) {
            return SPAWN;
        } else {
            return values()[currentState.ordinal() + 1];
        }
    }

    public static RotationState getAntiClockwiseRotationState(RotationState currentState) {
        if (currentState.ordinal() == 0) {
            return CLOCKWISE270;
        } else {
            return values()[currentState.ordinal() - 1];
        }
    }
}
