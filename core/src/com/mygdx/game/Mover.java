package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Mover {
    public Mover() {
    }

    private final MutableMovementResult result = new MutableMovementResult(MovementResult.MovementType.LinearMovement, false);

    public MovementResult movePiece(Playfield field, int x, int y) {
        boolean isMoved = false;
        int pieceRow = field.getActivePieceRow();
        int pieceCol = field.getActivePieceCol();
        Tetrimino piece = field.getActivePiece();
        if (piece != null) {
            field.unmergeActivePiece();
            if (field.isSpaceAvaiable(pieceRow + y, pieceCol + x, piece)) {
                field.setActivePieceCol(pieceCol + x);
                field.setActivePieceRow(pieceRow + y);
                isMoved = true;
            }
            field.mergeActivePiece();
        }
        result.setSuccess(isMoved);
        return result;
    }
}
