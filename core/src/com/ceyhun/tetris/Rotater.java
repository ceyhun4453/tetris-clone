package com.ceyhun.tetris;

public interface Rotater {
    MovementResult rotatePiece(Playfield field, int direction);
}
