package com.mygdx.game;

public class TetriminoHolder {

    private Tetrimino savedPiece;
    private boolean isSwapped;

    public TetriminoHolder() {
        isSwapped = false;
        savedPiece = null;
    }

    public HoldResult holdPiece(Tetrimino piece) {
        if (savedPiece == null) {
            savedPiece = piece;
            return new HoldResult(true, null);
        }

        if (isSwapped) {
            return new HoldResult(false, null);
        }

        Tetrimino temp = savedPiece;
        savedPiece = piece;
        isSwapped = true;
        return new HoldResult(true, temp);
    }

    public void resetSwapState() {
        isSwapped = false;
    }

    public class HoldResult {
        private boolean isSaved;
        private Tetrimino piece;

        private HoldResult(boolean isSaved, Tetrimino piece) {
            this.isSaved = isSaved;
            this.piece = piece;
        }

        public boolean isSaved() {
            return isSaved;
        }

        public Tetrimino getPiece() {
            return piece;
        }
    }


}
