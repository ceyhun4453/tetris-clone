package com.mygdx.game;

public class MovementHandler implements Translater, Rotater {

    private Translater translater;
    private Rotater rotater;
    private MutableMovementResult lastSuccessfulMovement = null;

    public MovementHandler(Translater translater, Rotater rotater) {
        this.translater = translater;
        this.rotater = rotater;
        lastSuccessfulMovement = new MutableMovementResult(null, false);
    }

    public MovementResult getLastSuccessfulMovement() {
        return lastSuccessfulMovement;
    }

    @Override
    public MovementResult movePiece(Playfield field, int x, int y) {
        MovementResult r = translater.movePiece(field, x, y);
        if (r.isSuccessful()) {
            lastSuccessfulMovement.setMovementType(r.getMovementType());
            lastSuccessfulMovement.setSuccess(r.isSuccessful());
        }
        return r;
    }

    @Override
    public MovementResult rotatePiece(Playfield field, int direction) {
        MovementResult r = rotater.rotatePiece(field, direction);
        if (r.isSuccessful()) {
            lastSuccessfulMovement.setMovementType(r.getMovementType());
            lastSuccessfulMovement.setSuccess(r.isSuccessful());
        }

        return r;
    }
}
