package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Gravity {

    private final float fallTime = 0.75f;
    private final float lockTime = 0.5f;

    private float lockTimer = 0.0f;
    private float fallCounter = 0.0f;
    private float fallModifier = 1.0f;
    private GravityState currentState;
    private final SoftDropState softDropState;
    private final RegularGravityState regularGravityState;
    private final HardDropState hardDropState;

    public Gravity() {
        regularGravityState = new RegularGravityState();
        softDropState = new SoftDropState();
        hardDropState = new HardDropState();
        currentState = regularGravityState;
    }

    public boolean gravitate(Playfield playfield, Mover mover, float deltaT) {
        if (isLockingDown(playfield)) {
            return handleLock(playfield, deltaT);
        }
        lockTimer = 0;
        currentState.gravitate(playfield, mover, this, deltaT);
        if (currentState.equals(hardDropState)) {
            currentState = regularGravityState;
            lockTimer = lockTime;
        }
        return true;
    }

    private void progressLockdown(float deltaT) {
        lockTimer += deltaT;
    }

    private boolean isLockingDown(Playfield playfield) {
        playfield.unmergeActivePiece();
        if (!playfield.isSpaceAvaiable(playfield.getActivePieceRow() - 1, playfield.getActivePieceCol(), playfield.getActivePiece())) {
            playfield.mergeActivePiece();
            return true;
        }
        playfield.mergeActivePiece();
        return false;
    }

    //Returns true if the piece should be locked in place. Returns false if there is still some time left.
    private boolean handleLock(Playfield playfield, float deltaT) {
        if (lockTimer > lockTime) {
            lockTimer = 0;
            return false;
        }
        progressLockdown(deltaT);
        return true;

    }

    public void stepUpFallSpeed() {
        fallModifier += 0.2f;
    }

    public void startSoftDrop() {
        currentState = softDropState;
    }

    public void endSoftDrop() {
        currentState = regularGravityState;
    }

    public void hardDrop() {
        currentState = hardDropState;
    }

    interface GravityState {
        boolean gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT);
    }

    private static class RegularGravityState implements GravityState {

        @Override
        public boolean gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT) {
            gravity.fallCounter += deltaT * gravity.fallModifier;
            while (gravity.fallCounter > gravity.fallTime) {
                if (playfield.getActivePiece() == null) {
                    return false;
                }
                mover.movePiece(playfield, 0, -1);
                gravity.fallCounter -= gravity.fallTime;
            }
            return true;
        }
    }

    private static class SoftDropState implements GravityState {
        private final int softDropModifier = 20;
        private final RegularGravityState regularGravityState;

        public SoftDropState() {
            regularGravityState = new RegularGravityState();
        }

        @Override
        public boolean gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT) {
            return regularGravityState.gravitate(playfield, mover, gravity, deltaT * softDropModifier);
        }
    }

    private static class HardDropState implements GravityState {

        @Override
        public boolean gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT) {
            while (mover.movePiece(playfield, 0, -1));
            gravity.fallCounter = 0;
            return true;
        }
    }
}
