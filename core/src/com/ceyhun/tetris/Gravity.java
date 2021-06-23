package com.ceyhun.tetris;

public class Gravity {

    private final float fallTime = 0.75f;
    private final float lockTime = 0.5f;

    private float lockTimer = 0.0f;
    private float fallCounter = 0.0f;
    private float fallModifier = 1.0f;
    private GravityHandler currentState;

    public Gravity() {
        currentState = GravityState.RegularGravity;
    }

    public GravityEvent gravitate(Playfield playfield, Translater mover, float deltaT) {
        if (isLockingDown(playfield)) {
            return handleLock(playfield, deltaT);
        }
        lockTimer = 0;
        return currentState.gravitate(playfield, mover, this, deltaT);
    }

    private void progressLockdown(float deltaT) {
        lockTimer += deltaT;
    }

    private boolean isLockingDown(Playfield playfield) {
        playfield.unmergeActivePiece();
        if (!playfield.isSpaceAvailable(playfield.getActivePieceRow() - 1, playfield.getActivePieceCol(), playfield.getActivePiece())) {
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
            return new GravityEvent(false, 0, GravityState.RegularGravity);
        }
        progressLockdown(deltaT);
        return new GravityEvent(true, 0, GravityState.RegularGravity);

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
        GravityEvent gravitate(Playfield playfield, Translater mover, Gravity gravity, float deltaT);
    }

    public enum GravityState implements GravityHandler {
        RegularGravity {
            @Override
            public GravityEvent gravitate(Playfield playfield, Translater mover, Gravity gravity, float deltaT) {
                gravity.fallCounter += deltaT * gravity.fallModifier;
                GravityEvent gravityEvent = new GravityEvent();
                while (gravity.fallCounter > gravity.fallTime) {
                    if (playfield.getActivePiece() == null) {
                        return new GravityEvent(false, 0, RegularGravity);
                    }
                    if (mover.movePiece(playfield, 0, -1).isSuccessful()) {
                        gravityEvent.numberOfCellsMoved += 1;
                    }
                    gravity.fallCounter = 0;
                }
                gravityEvent.state = this;
                gravityEvent.isPieceInPlay = true;
                return gravityEvent;
            }
        },

        SoftDrop {
            private final int softDropModifier = 20;

            @Override
            public GravityEvent gravitate(Playfield playfield, Translater mover, Gravity gravity, float deltaT) {
                GravityEvent gravityEvent = RegularGravity.gravitate(playfield, mover, gravity, softDropModifier * deltaT);
                gravityEvent.state = SoftDrop;
                return gravityEvent;
            }
        },

        HardDrop {
            @Override
            public GravityEvent gravitate(Playfield playfield, Translater mover, Gravity gravity, float deltaT) {
                int fallCounter = 0;
                while (mover.movePiece(playfield, 0, -1).isSuccessful()) {
                    fallCounter++;
                }
                gravity.currentState = GravityState.RegularGravity;
                gravity.lockTimer = gravity.lockTime;
                return new GravityEvent(true, fallCounter, this);
            }
        };
    }

    // TODO: rename this class.
    public static class GravityEvent {
        private boolean isPieceInPlay;
        private int numberOfCellsMoved;
        private GravityState state;

        public GravityEvent() {
        }

        private GravityEvent(boolean isPieceInPlay, int numberOfCellsMoved, GravityState state) {
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
