package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Hud {

    private final String levelLabel = "Level : ";
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Assets assets;
    private final ShapeRenderer shapeRenderer;


    private BitmapFont bitmapFont;
    private int level;
    private int score;
    private Tetrimino savedPiece;

    public Hud(SpriteBatch batch, ShapeRenderer shapeRenderer, Assets assets) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 160, 90);
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.assets = assets;
        bitmapFont = assets.getFontAssets().microsoftSans;
        score = 0;
        level = 1;
    }

    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, 160, 90);
        shapeRenderer.end();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int size = 3;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                batch.draw(assets.getSprites().wall, 30 + (size * i), 45 + (size * j), size, size);
            }
        }
        batch.draw(assets.getSprites().background, 33, 48, 12, 12);
        if (savedPiece != null) {
            drawHeldPiece(36, 48, 3);
        }
        
        bitmapFont.getData().setScale(0.07f);
        bitmapFont.draw(batch, String.valueOf(score), 32, 82.5f);
        bitmapFont.draw(batch, levelLabel + level, 32, 75.0f);
        batch.end();
    }

    private void drawHeldPiece(int x, int y, int size) {
        for (int i = 0; i < savedPiece.getLength(); i++) {
            for (int j = 0; j < savedPiece.getLength(); j++) {
                if (savedPiece.getValue(i , j) == savedPiece.getTetrominoType().getConstant()) {
                    batch.draw(getTextureRegionForPiece(savedPiece), x + j * size, y + i * size, size, size);
                }
            }
        }
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void updateSavedPiece(Tetrimino piece) {
        this.savedPiece = piece;
    }

    public void updateLevel(int level) {
        this.level = level;
    }

    /* TODO: This is nearly a copy-paste of the same method from TetrisRenderer.
        Find a way to reuse the original method without copy pasting.
     */
    private TextureRegion getTextureRegionForPiece(Tetrimino tetrimino) {
        switch (tetrimino.getTetrominoType()) {
            case I:
                return assets.getSprites().iPiece;
            case J:
                return assets.getSprites().jPiece;
            case Z:
                return assets.getSprites().zPiece;
            case S:
                return assets.getSprites().sPiece;
            case T:
                return assets.getSprites().tPiece;
            case O:
                return assets.getSprites().oPiece;
            case L:
                return assets.getSprites().lPiece;
            default:
                return null;
        }
    }
}
