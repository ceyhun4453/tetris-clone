package com.mygdx.game;

public class TetriminoSource {

    private TetriminoGenerator generator;
    private TetriminoHolder holder;

    public TetriminoSource(TetriminoGenerator generator, TetriminoHolder holder) {
        this.generator = generator;
        this.holder = holder;
    }

    public Tetrimino createRandomTetrimino() {
        holder.resetSwapState();
        return generator.getRandomTetrimino();
    }

    public TetriminoHolder.HoldResult holdPiece(Tetrimino piece) {
        return holder.holdPiece(piece);
    }
}
