package com.ceyhun.tetris;

public interface Translater {

    MovementResult movePiece(Playfield field, int x, int y);
}
