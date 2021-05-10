package com.mygdx.game;

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

    public boolean gravitate(Playfield playfield, Mover mover, float deltaT, int level) {
        if (isLockingDown(playfield)) {
            return handleLock(playfield, deltaT);
        }
        lockTimer = 0;
        currentState.gravitate(playfield, mover, this, deltaT, level);
        if (currentState.equals(GravityState.HardDrop)) {
            currentState = GravityState.RegularGravity;
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
        currentState = GravityState.SoftDrop;
    }

    public void endSoftDrop() {
        currentState = GravityState.RegularGravity;
    }

    public void hardDrop() {
        currentState = GravityState.HardDrop;
    }

    interface GravityHandler {
        GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT, int level);
    }

    public enum GravityState implements GravityHandler {
        RegularGravity {
            @Override
            public GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT, int level) {
                gravity.fallCounter += deltaT * gravity.fallModifier * (1 + (level - 1) * 0.1);
                while (gravity.fallCounter > gravity.fallTime) {
                    if (playfield.getActivePiece() == null) {
                        gravity.gravityEvent.setFields(false, 0, RegularGravity);
                    }
                    if (mover.movePiece(playfield, 0, -1)) {
                        gravity.gravityEvent.numberOfCellsMoved = 1;
                    }
                    gravity.fallCounter -= gravity.fallTime;
                }
                return true;
            }
        },

        SoftDrop {
            private final int softDropModifier = 20;

            @Override
            public GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT, int level) {
                return RegularGravity.gravitate(playfield, mover, gravity, softDropModifier * deltaT, level);
            }
        },

        HardDrop {
            @Override
            public GravityEvent gravitate(Playfield playfield, Mover mover, Gravity gravity, float deltaT, int level) {
                while (mover.movePiece(playfield, 0, -1)) ;
                gravity.fallCounter = 0;
                return true;
            }
        };
    }

    // TODO: rename this class.
    private static class GravityEvent {
        private boolean isPieceInPlay;
        private int numberOfCellsMoved;
        private GravityState state;

        public GravityEvent(boolean isPieceInPlay, int numberOfCellsMoved, GravityState state) {
            this.isPieceInPlay = isPieceInPlay;
            this.numberOfCellsMoved = numberOfCellsMoved;
            this.state = state;
        }

        private void setFields(boolean isPieceInPlay, int numberOfCellsMoved, GravityState state) {
            this.isPieceInPlay = isPieceInPlay;
            this.numberOfCellsMoved = numberOfCellsMoved;
            this.state = state;
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
