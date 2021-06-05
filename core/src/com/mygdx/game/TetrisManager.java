package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TetrisManager implements InputProcessor {

    private static final int clearsPerLevel = 10;
    private final int newPieceHorizontalPosition = 4;

    private final Playfield field;
    private final MovementHandler movementHandler;
    private final Clearer clearer;
    private final Gravity gravity;
    private final Scorer scorer;
    private final TetriminoGenerator generator;
    private final TetrisRenderer renderer;

    private int newPieceVerticalPosition = 21;
    private int currentLevel = 1;
    private int clearedRows = 0;

    public TetrisManager(SpriteBatch batch, Assets assets) {
        field = new Playfield();
        movementHandler = new MovementHandler();
        clearer = new Clearer();
        scorer = new Scorer();
        renderer = new TetrisRenderer(field, batch, assets);
        generator = new TetriminoGenerator();
        gravity = new Gravity();
    }

    public void loop(float deltaT) {
        if (field.getActivePiece() == null) {
            addNewPiece(generator.getRandomTetrimino());
        }

        Gravity.GravityEvent gravityEvent = gravity.gravitate(field, movementHandler, deltaT);
        scorer.updateScoreWithGravity(gravityEvent);
        if(!gravityEvent.isPieceInPlay()) {
            Clearer.Clear lastClear = clearer.clearFullRows(field, movementHandler.getLastSuccesfulMovement());
            int lastClearedRows = lastClear.getNumberOfLines();
            Gdx.app.log("CLEAR TYPE", lastClear.getClearType().toString());
            clearedRows += lastClearedRows;
            Gdx.app.log("CLEARED ROWS: ", String.valueOf(clearedRows));
            scorer.updateScoreWithClear(lastClearedRows, currentLevel);
            if (clearedRows >= clearsPerLevel) {
                currentLevel++;
                gravity.stepUpFallSpeed();
                clearedRows -= clearsPerLevel;
                Gdx.app.log("LEVEL", String.valueOf(currentLevel));
            }
            addNewPiece(generator.getRandomTetrimino());
        }
        renderer.render();
    }

    private void addNewPiece(Tetrimino newPiece) {
        if (newPieceVerticalPosition > 24) {
            // GAMEOVER
            return;
        }
        if (!field.isSpaceAvaiable(newPieceVerticalPosition, newPieceHorizontalPosition, newPiece)) {
            newPieceVerticalPosition++;
            addNewPiece(newPiece);
        }
        field.setActivePieceRow(newPieceVerticalPosition);
        field.setActivePieceCol(newPieceHorizontalPosition);
        field.setActivePiece(newPiece);
        field.mergeActivePiece();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                movementHandler.movePiece(field, -1, 0);
                return true;
            case Input.Keys.RIGHT:
                movementHandler.movePiece(field, 1, 0);
                return true;
            case Input.Keys.X:
                movementHandler.rotatePiece(field, SimpleRotater.CLOCKWISE);
                return true;
            case Input.Keys.Z:
                movementHandler.rotatePiece(field, SimpleRotater.ANTICLOCKWISE);
                return true;
            case Input.Keys.DOWN:
                gravity.startSoftDrop();
                return true;
            case Input.Keys.SPACE:
                gravity.hardDrop();
                return true;
            default: return false;
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            gravity.endSoftDrop();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
