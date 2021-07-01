package com.ceyhun.tetris;

import com.badlogic.gdx.math.Vector2;

public class MovementHandler implements Translater, Rotater {

    private Translater translater;
    private Rotater rotater;
    private Movement lastSuccesfulMovement = null;

    public MovementHandler() {
        this.translater = new SimpleTranslater();
        this.rotater = new SimpleRotater(this);
    }

    @Override
    public MovementResult movePiece(Playfield field, int x, int y) {
        Vector2 currentPosition = new Vector2(field.getActivePieceCol(), field.getActivePieceRow());
        RotationState currentRotationState = field.getActivePiece().getRotationState();
        MovementResult r = translater.movePiece(field, x, y);
        if (r.isSuccessful()) {
            lastSuccesfulMovement = new Movement(currentPosition, new Vector2(field.getActivePieceCol(),
                    field.getActivePieceRow()), currentRotationState, field.getActivePiece().getRotationState());
        }
        return r;
    }

    @Override
    public MovementResult rotatePiece(Playfield field, int direction) {
        Vector2 currentPosition = new Vector2(field.getActivePieceCol(), field.getActivePieceRow());
        RotationState currentRotationState = field.getActivePiece().getRotationState();
        MovementResult r = rotater.rotatePiece(field, direction);
        if (r.isSuccessful()) {
            lastSuccesfulMovement = new Movement(currentPosition, new Vector2(field.getActivePieceCol(),
                    field.getActivePieceRow()), currentRotationState, field.getActivePiece().getRotationState());
        }
        return r;
    }

    public Movement getLastSuccesfulMovement() {
        return lastSuccesfulMovement;
    }
}
