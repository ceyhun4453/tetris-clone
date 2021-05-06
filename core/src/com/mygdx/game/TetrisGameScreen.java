package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TetrisGameScreen implements Screen {
    private static final int unitLength = 10;

    private final TetrisManager tetrisManager;
    private final Assets assets;
    private final SpriteBatch batch;

    public TetrisGameScreen() {
        assets = new Assets(new AssetManager());
        batch = new SpriteBatch();
        tetrisManager = new TetrisManager(batch, assets);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(tetrisManager);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(150f / 255f, 80f / 255f, 60f / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tetrisManager.loop(delta);
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
}
