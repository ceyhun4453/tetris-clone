package com.ceyhun.tetris;

public class SimpleTranslater implements Translater {



    public SimpleTranslater() {
    }

    @Override
    public MovementResult movePiece(Playfield field, int x, int y) {
        boolean isMoved = false;
        int pieceRow = field.getActivePieceRow();
        int pieceCol = field.getActivePieceCol();
        Tetrimino piece = field.getActivePiece();
        if (piece != null) {
            field.unmergeActivePiece();
            if (field.isSpaceAvailable(pieceRow + y, pieceCol + x, piece)) {
                field.setActivePieceCol(pieceCol + x);
                field.setActivePieceRow(pieceRow + y);
                isMoved = true;
            }

            if (isMoved) {
                moveGhost(field);
            }
            field.mergeActivePiece();
        }
        return new MutableMovementResult(MovementResult.MovementType.LinearMovement, isMoved);
    }



    private void moveGhost(Playfield field) {
        int ghostRow = field.getActivePieceRow();
        int ghostCol = field.getActivePieceCol();
        while (field.isSpaceAvailable(ghostRow - 1, ghostCol, field.getActivePiece())) {
            ghostRow -= 1;
        }
        field.updateGhost(ghostRow, ghostCol);
    }
}
