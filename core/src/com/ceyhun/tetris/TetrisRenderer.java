package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TetrisRenderer {
    private static final int visibleColumns = 10;
    private static final int visibleRows = 20;
    private final OrthographicCamera camera;
    private final Assets assets;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final Playfield playfield;

    public TetrisRenderer(Playfield playfield, SpriteBatch batch, ShapeRenderer shapeRenderer, Assets assets) {
        this.playfield = playfield;
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, visibleColumns + 2, visibleRows + 1);
        this.assets = assets;
    }

    public void render() {
        int height = Gdx.graphics.getHeight() - 50;
        int width = height / 2;
        Gdx.gl.glViewport((Gdx.graphics.getWidth() - width) / 2, 25, width, height);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderBackground();
        renderPlayfield();
        batch.end();
    }

    public void renderBackground() {
        for (int i = 0; i < camera.viewportWidth; i++) {
            for (int j = 0; j < camera.viewportHeight; j++) {
                batch.draw(assets.getSprites().wall, i, j, 1, 1);
            }
        }
        batch.draw(assets.getSprites().background, 1, 1, visibleColumns, visibleRows);
    }

    public void renderPlayfield() {
        for (int row = 0; row < playfield.getPlayAreaHeight(); row++) {
            for (int col = 0; col < playfield.getPlayAreaWidth(); col++) {
                int value = playfield.getValue(row, col);
                if (value != 0) {
                    TextureRegion region = getTextureForPieceValue(value);
                    if (region != null) {
                        batch.draw(region, col + 1, row + 1, 1, 1);
                    }
                }
            }
        }
    }

    public void drawBorder() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, visibleColumns + 2, visibleRows + 1);
        shapeRenderer.end();
    }

    private TextureRegion getTextureForPieceValue(int value) {
        if (value == Playfield.GHOST) {
            return assets.getSprites().ghostPiece;
        }

        if (value == Playfield.WALL_VALUE) {
            return assets.getSprites().wall;
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
}
