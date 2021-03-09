package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen, InputProcessor {
    private static final int unitLength = 10;

    private final Playfield playfield;
    private final ShapeRenderer renderer;
    private final OrthographicCamera camera;
    private final Assets assets;
    private final SpriteBatch batch;

    public GameScreen() {
        playfield = new Playfield();
        renderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, playfield.getPlayAreaWidth() * unitLength, playfield.getPlayAreaHeight() * unitLength);
        renderer.setProjectionMatrix(camera.combined);
        assets = new Assets(new AssetManager());
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(150f / 255f, 80f / 255f, 60f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playfield.update(delta);
        drawBackground(renderer);
        drawPlayfield(playfield);
        Gdx.app.log("FRAME RATE: ", String.valueOf((1.0f / delta)));
    }

    private void drawBackground(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < playfield.getPlayAreaWidth(); i++) {
            if (i % 2 == 0) {
                renderer.setColor(0.8f, 0.632f, 0.632f, 1);
            } else {
                renderer.setColor(0.85f, 0.8415f, 0.8415f, 1);
            }
            renderer.rect(i * unitLength, 0, unitLength, camera.viewportHeight);
        }
        renderer.end();
    }

    private void drawPlayfield(Playfield playfield) {
        batch.begin();
        for (int row = 0; row < playfield.getPlayAreaHeight(); row++) {
            for (int col = 0; col < playfield.getPlayAreaWidth(); col++) {
                int value = playfield.getPlayAreaValue(row, col);
                if (value != 0) {
                    TextureRegion region = getTextureForPieceValue(value);
                    if (region != null) {
                        batch.draw(region, col * unitLength, row * unitLength, unitLength, unitLength);
                    }
                }
            }
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    private TextureRegion getTextureForPieceValue(int value) {

        if (value == 9) {
            return assets.getSprites().tPiece;
        }
        Tetrimino.TetriminoType t = Tetrimino.TetriminoType.getTypeForConstant(value);
        switch (t) {
            case I:
                return assets.getSprites().iPiece;
            case J:
                return assets.getSprites().jPiece;
            case L:
                return assets.getSprites().lPiece;
            case O:
                return assets.getSprites().oPiece;
            case S:
                return assets.getSprites().sPiece;
            case T:
                return assets.getSprites().tPiece;
            case Z:
                return assets.getSprites().zPiece;
        }

        return null;
    }

    private boolean downToggle = false;
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                playfield.rotateActivePiece();
                return true;
            case Input.Keys.LEFT:
                playfield.moveActivePiece(-1, 0);
                return true;
            case Input.Keys.RIGHT:
                playfield.moveActivePiece(1, 0);
                return true;
            case Input.Keys.DOWN:
                playfield.setSpeedUp(true);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            playfield.setSpeedUp(false);
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
