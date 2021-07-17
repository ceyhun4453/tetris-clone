package com.ceyhun.tetris;

public class TetriminoSource {

    private final int spawnCol = 4;
    private final int maxSpawnRow = 24;

    private int spawnRow = 21;
    private TetriminoGenerator generator;
    private TetriminoHolder holder;

    public TetriminoSource(TetriminoGenerator generator, TetriminoHolder holder) {
        this.generator = generator;
        this.holder = holder;
    }

    // It will add a piece to the field IF there is currently no piece in play, or
    // the active is locked in the current frame.
    public boolean attemptToAdd(Playfield field, Gravity.GravityEvent lastGravityEvent) {
        // Add a new piece if the the previous piece is locked.
        if (field.getActivePiece() == null) {
            return addNewPiece(field, generator.getRandomTetrimino());
        }

        if (!lastGravityEvent.isPieceInPlay()) {
            holder.resetSwapState();
            return addNewPiece(field, generator.getRandomTetrimino());
        }

        return true;
    }

    public boolean holdPiece(Playfield field) {
        TetriminoHolder.HoldResult result = holder.holdPiece(field.getActivePiece());
        if (result.getPiece() == null && result.isSaved()) {
            field.unmergeActivePiece();
            addNewPiece(field, generator.getRandomTetrimino());
            return true;
        } else if (result.getPiece() != null && result.isSaved()) {
            field.unmergeActivePiece();
            addNewPiece(field, result.getPiece());
            return true;
        }
        return false;
    }

    private boolean addNewPiece(Playfield field, Tetrimino newPiece) {
        if (spawnRow > maxSpawnRow) {
            // GAMEOVER
            return false;
        }
        if (!field.isSpaceAvailable(spawnRow, spawnCol, newPiece)) {
            spawnRow++;
            return addNewPiece(field, newPiece);
        }
        field.setActivePieceRow(spawnRow);
        field.setActivePieceCol(spawnCol);
        field.setActivePiece(newPiece);
        field.mergeActivePiece();
        return true;
    }
}
