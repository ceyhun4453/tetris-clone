package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TetrisManager {

    private static final int clearsPerLevel = 10;

    private final Playfield field;
    private final MovementHandler movementHandler;
    private final Clearer clearer;
    private final Gravity gravity;
    private final Scorer scorer;
    private final TetriminoSource source;
    private final TetrisRenderer renderer;
    private final Hud hud;
    private final Autorepeat autorepeat;
    private final TetrisInputHandler inputHandler;
    private int currentLevel = 1;
    private int clearedRows = 0;

    public TetrisManager(SpriteBatch batch, ShapeRenderer shapeRenderer, Assets assets) {
        field = new Playfield();
        movementHandler = new MovementHandler();
        clearer = new Clearer();
        scorer = new Scorer();
        renderer = new TetrisRenderer(field, batch, shapeRenderer, assets);
        source = new TetriminoSource(new TetriminoGenerator(), new TetriminoHolder());
        gravity = new Gravity();
        autorepeat = new Autorepeat(movementHandler);
        inputHandler = new TetrisInputHandler();
        registerCommands();
        Gdx.input.setInputProcessor(inputHandler);
        source.attemptToAdd(field, null);
        hud = new Hud(batch, shapeRenderer,assets);
    }

    private void registerCommands() {
        inputHandler.registerCommand(Input.Keys.LEFT, TetrisInputHandler.KEY_DOWN, () -> {
            if (!autorepeat.isOn()) {
                movementHandler.movePiece(field, -1, 0);
            }
        });
        inputHandler.registerCommand(Input.Keys.RIGHT, TetrisInputHandler.KEY_DOWN, () -> {
            if (!autorepeat.isOn()) {
                movementHandler.movePiece(field, 1, 0);
            }
        });
        inputHandler.registerCommand(Input.Keys.X, TetrisInputHandler.KEY_DOWN,
                () -> movementHandler.rotatePiece(field, Rotater.CLOCKWISE));
        inputHandler.registerCommand(Input.Keys.Z, TetrisInputHandler.KEY_DOWN,
                () -> movementHandler.rotatePiece(field, Rotater.ANTICLOCKWISE));
        inputHandler.registerCommand(Input.Keys.DOWN, TetrisInputHandler.KEY_DOWN, gravity::startSoftDrop);
        inputHandler.registerCommand(Input.Keys.SPACE, TetrisInputHandler.KEY_DOWN, gravity::hardDrop);
        inputHandler.registerCommand(Input.Keys.C, TetrisInputHandler.KEY_DOWN, () -> {
            Tetrimino piece = field.getActivePiece();
            if (source.holdPiece(field)) {
                hud.updateSavedPiece(piece);
            }
        });
        inputHandler.registerCommand(Input.Keys.DOWN, TetrisInputHandler.KEY_UP, gravity::endSoftDrop);
        inputHandler.registerCommand(Input.Keys.LEFT, TetrisInputHandler.KEY_UP, () -> autorepeat.stopAutorepeat(Autorepeat.LEFT));
        inputHandler.registerCommand(Input.Keys.RIGHT, TetrisInputHandler.KEY_UP, () -> autorepeat.stopAutorepeat(Autorepeat.RIGHT));
        inputHandler.registerCommand(Input.Keys.RIGHT, TetrisInputHandler.KEY_HELD,
                () -> autorepeat.stepAutorepeat(Autorepeat.RIGHT, field));
        inputHandler.registerCommand(Input.Keys.LEFT, TetrisInputHandler.KEY_HELD,
                () -> autorepeat.stepAutorepeat(Autorepeat.LEFT, field));
    }

    public void loop(float deltaT) {
        inputHandler.processHeldInputs();
        Gravity.GravityEvent gravityEvent = gravity.gravitate(field, movementHandler, deltaT);
        scorer.updateScoreWithGravity(gravityEvent);
        if (!gravityEvent.isPieceInPlay()) {
            Clearer.Clear lastClear = clearer.clearFullRows(field, movementHandler.getLastSuccesfulMovement());
            int lastClearedRows = lastClear.getNumberOfLines();
            Gdx.app.log("CLEAR TYPE", lastClear.getClearType().toString());
            clearedRows += lastClearedRows;
            scorer.updateScoreWithClear(lastClearedRows, currentLevel);
            if (clearedRows >= clearsPerLevel) {
                currentLevel++;
                hud.updateLevel(currentLevel;
                gravity.stepUpFallSpeed();
                clearedRows -= clearsPerLevel;
            }
        }
        if (!source.attemptToAdd(field, gravityEvent)) {
            //GAMEOVER
            System.out.println("Game over!");
        }
        hud.updateScore(scorer.getScore());
        hud.render();
        renderer.render();
    }
}
