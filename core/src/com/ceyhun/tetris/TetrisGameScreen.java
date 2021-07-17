package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TetrisGameScreen implements Screen {

    private final TetrisManager tetrisManager;
    private final Assets assets;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

    public TetrisGameScreen() {
        assets = new Assets(new AssetManager());
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tetrisManager = new TetrisManager(batch, shapeRenderer, assets);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(150f / 255f, 80f / 255f, 60f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tetrisManager.loop(delta);
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
        shapeRenderer.dispose();
        batch.dispose();
        assets.dispose();
    }
}
