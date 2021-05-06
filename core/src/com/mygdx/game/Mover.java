package com.mygdx.game;

public class Mover {
    public Mover() {
    }

    public boolean movePiece(Playfield field, int x, int y) {
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
        return isMoved;
    }
}
