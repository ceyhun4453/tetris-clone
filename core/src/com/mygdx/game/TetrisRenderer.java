package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;

public class TetrisRenderer {
    private static final int unitLength = 10;
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
        camera.setToOrtho(false, unitLength * visibleColumns, unitLength * visibleRows);
        batch.setProjectionMatrix(camera.combined);
        this.assets = assets;
    }

    public void render() {
        renderBackground();
        renderPlayfield();
    }

    public void renderBackground() {
        batch.draw(assets.getSprites().background, 0, 0);
    }

    public void renderPlayfield() {
        for (int row = 0; row < playfield.getPlayAreaHeight(); row++) {
            for (int col = 0; col < playfield.getPlayAreaWidth(); col++) {
                int value = playfield.getValue(row, col);
                if (value != 0) {
                    TextureRegion region = getTextureForPieceValue(value);
                    if (region != null) {
                        batch.draw(region, col * unitLength, row * unitLength, unitLength, unitLength);
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
