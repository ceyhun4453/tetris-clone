package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TetrisRenderer {
    private static final int visibleColumns = 10;
    private static final int visibleRows = 20;
    private final OrthographicCamera camera;
    private final Assets assets;
    private final SpriteBatch batch;
    private final Playfield playfield;

    public TetrisRenderer(Playfield playfield, SpriteBatch batch, Assets assets) {
        this.playfield = playfield;
        this.batch = batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, visibleColumns, visibleRows);
        batch.setProjectionMatrix(camera.combined);
        this.assets = assets;
    }

    public void render() {
        int height = Gdx.graphics.getHeight();
        int width = height / 2;
        Gdx.gl.glViewport((Gdx.graphics.getWidth() - width) / 2, 0, width, height);
        renderBackground();
        renderPlayfield();
    }

    public void renderBackground() {
        batch.draw(assets.getSprites().background, 0, 0, visibleColumns, visibleRows);
    }

    public void renderPlayfield() {
        for (int row = 0; row < playfield.getPlayAreaHeight(); row++) {
            for (int col = 0; col < playfield.getPlayAreaWidth(); col++) {
                int value = playfield.getValue(row, col);
                if (value != 0) {
                    TextureRegion region = getTextureForPieceValue(value);
                    if (region != null) {
                        batch.draw(region, col, row, 1, 1);
                    }
                }
            }
        }
    }

    private TextureRegion getTextureForPieceValue(int value) {
        if (value == Playfield.GHOST) {
            return assets.getSprites().ghostPiece;
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
