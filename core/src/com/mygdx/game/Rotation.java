package com.mygdx.game;

import java.util.Objects;

public class Rotation {

    private RotationState startState;
    private RotationState endState;
    private boolean isChangeable;
    public Rotation(boolean isChangable) {
        this.isChangeable = isChangable;
    }

    public Rotation(RotationState startState, RotationState endState, boolean isChangeable) {
        this(isChangeable);
        this.startState = startState;
        this.endState = endState;
    }

    // Sets the startState of this Rotation ONLY IF isChangeable is true.
    public void setStartState(RotationState startState) {
        if (isChangeable) {
            this.startState = startState;
        }
    }

    // Sets the endState of this Rotation ONLY IF isChangeable is true.
    public void setEndState(RotationState endState) {
        if (isChangeable) {
            this.endState = endState;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rotation rotation = (Rotation) o;
        return startState == rotation.startState &&
                endState == rotation.endState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startState, endState);
    }
}
