package com.ceyhun.tetris;

public class MutableMovementResult implements MovementResult{

    private MovementType movementType;
    private boolean success;

    public MutableMovementResult(MovementType movementType, boolean success) {
        this.movementType = movementType;
        this.success = success;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public MovementType movementType() {
        return movementType;
    }
}
