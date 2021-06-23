package com.ceyhun.tetris;

import java.util.Objects;

public class Rotation {

    private RotationState startState;
    private RotationState endState;

    public Rotation(RotationState startState, RotationState endState) {
        this.startState = startState;
        this.endState = endState;
    }

    public RotationState getStartState() {
        return startState;
    }

    public RotationState getEndState() {
        return endState;
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
