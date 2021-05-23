package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class MovementHandler implements Translater, Rotater {

    private Translater translater;
    private Rotater rotater;
    private Movement movement;

    public MovementHandler() {
        this.translater = new SimpleTranslater();
        this.rotater = new SimpleRotater(this);
    }

    @Override
    public MovementResult movePiece(Playfield field, int x, int y) {
        MovementResult r = translater.movePiece(field, x, y);
        if (r.isSuccessful()) {
        }
        return r;
    }

    @Override
    public MovementResult rotatePiece(Playfield field, int direction) {
        MovementResult r = rotater.rotatePiece(field, direction);
        if (r.isSuccessful()) {
        }
        return r;
    }

    public static class Movement {
        private RotationState currentRotationState;
        private RotationState previousRotationState;
        private Vector2 currentPosition;
        private Vector2 previousPosition;

        public RotationState getCurrentRotationState() {
            return currentRotationState;
        }

        public RotationState getPreviousRotationState() {
            return previousRotationState;
        }

        public Vector2 getCurrentPosition() {
            return currentPosition;
        }

        public Vector2 getPreviousPosition() {
            return previousPosition;
        }
    }
}
