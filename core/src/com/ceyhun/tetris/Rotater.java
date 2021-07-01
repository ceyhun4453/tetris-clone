package com.ceyhun.tetris;

public interface Rotater {
    public static final int CLOCKWISE = 1;
    public static final int ANTICLOCKWISE = -1;
    MovementResult rotatePiece(Playfield field, int direction);
}
