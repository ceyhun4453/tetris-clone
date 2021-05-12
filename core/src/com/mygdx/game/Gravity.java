package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Gravity {

    private final float fallTime = 0.75f;
    private final float lockTime = 0.5f;

    private float lockTimer = 0.0f;
    private float fallCounter = 0.0f;
    private float fallModifier = 1.0f;
    private GravityHandler currentState;
    private final GravityEvent gravityEvent;

    public Gravity() {
        currentState = GravityState.RegularGravity;
        gravityEvent = new GravityEvent(false, 0, GravityState.RegularGravity);
    }

    public GravityEvent gravitate(Playfield playfield, Mover mover, float deltaT) {
        if (isLockingDown(playfield)) {
            return handleLock(playfield, deltaT);
        }
        gravityEvent.reset();
        currentState.gravitate(playfield, mover, this, deltaT);
        if (currentState.equals(GravityState.HardDrop)) {
            currentState = GravityState.RegularGravity;
            lockTimer = lockTime;
        }
        return gravityEvent;
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
    private GravityEvent handleLock(Playfield playfield, float deltaT) {
        if (lockTimer > lockTime) {
            lockTimer = 0;
            gravityEvent.isPieceInPlay = false;
            return gravityEvent;
        }
        progressLockdown(deltaT);
        gravityEvent.isPieceInPlay = true;
        gravityEvent.numberOfCellsMoved = 0;
        return gravityEvent;

    }

    public void stepUpFallSpeed() {
        fallModifier += 0.2f;
    }

    public void startSoftDrop() {
        currentState = GravityState.SoftDrop;
    }

    public void endSoftDrop() {
        currentState = GravityState.RegularGravity;
    }

    public void hardDrop() {
        currentState = GravityState.HardDrop;
    }

    interface GravityHandler {
        GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT);
    }

    public enum GravityState implements GravityHandler {
        RegularGravity {
            @Override
            public GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT) {
                gravity.fallCounter += deltaT * gravity.fallModifier;
                while (gravity.fallCounter > gravity.fallTime) {
                    if (playfield.getActivePiece() == null) {
                        gravity.gravityEvent.setFields(false, 0, RegularGravity);
                    }
                    if (mover.movePiece(playfield, 0, -1)) {
                        gravity.gravityEvent.numberOfCellsMoved += 1;
                    }
                    gravity.fallCounter -= gravity.fallTime;
                }
                gravity.gravityEvent.state = RegularGravity;
                gravity.gravityEvent.isPieceInPlay = true;
                return gravity.gravityEvent;
            }
        },

        SoftDrop {
            private final int softDropModifier = 20;

            @Override
            public GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT) {
                RegularGravity.gravitate(playfield, mover, gravity, softDropModifier * deltaT);
                gravity.gravityEvent.state = SoftDrop;
                return gravity.gravityEvent;
            }
        },

        HardDrop {
            @Override
            public GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT) {
                int fallCounter = 0;
                while (mover.movePiece(playfield, 0, -1)) {
                    fallCounter++;
                }
                gravity.gravityEvent.setFields(true, fallCounter, HardDrop);
                return gravity.gravityEvent;
            }
        };
    }

    // TODO: rename this class.
    public static class GravityEvent {
        private boolean isPieceInPlay;
        private int numberOfCellsMoved;
        private GravityState state;

        private GravityEvent(boolean isPieceInPlay, int numberOfCellsMoved, GravityState state) {
            this.isPieceInPlay = isPieceInPlay;
            this.numberOfCellsMoved = numberOfCellsMoved;
            this.state = state;
        }

        private void setFields(boolean isPieceInPlay, int numberOfCellsMoved, GravityState state) {
            this.isPieceInPlay = isPieceInPlay;
            this.numberOfCellsMoved = numberOfCellsMoved;
            this.state = state;
        }

        private void reset() {
            state = GravityState.RegularGravity;
            numberOfCellsMoved = 0;
            isPieceInPlay = false;
        }

        public boolean isPieceInPlay() {
            return isPieceInPlay;
        }

        public int getNumberOfCellsMoved() {
            return numberOfCellsMoved;
        }

        public GravityState getGravityState() {
            return state;
        }
    }
}
