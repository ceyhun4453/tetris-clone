package com.mygdx.game;

import java.util.Objects;

public class Rotation {

    private RotationState startState;
    private RotationState endState;

    public Rotation() {
    }

    public Rotation(RotationState startState, RotationState endState) {
        this.startState = startState;
        this.endState = endState;
    }

    // Sets the startState of this Rotation ONLY IF isChangeable is true.
    public void setStartState(RotationState startState) {
            this.startState = startState;
    }

    // Sets the endState of this Rotation ONLY IF isChangeable is true.
    public void setEndState(RotationState endState) {
            this.endState = endState;
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
