package com.ceyhun.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    private static final AssetDescriptor<TextureAtlas> textureAtlasDescriptor =
            new AssetDescriptor<>(Gdx.files.internal("tetris.atlas"), TextureAtlas.class);

    private AssetManager manager;
    public final SpriteAssets sprites;
    public final FontAssets fontAssets;
    public Assets(AssetManager manager) {
        this.manager = manager;
        this.manager.load(textureAtlasDescriptor);
        this.manager.finishLoading();
        sprites = new SpriteAssets(manager.get(textureAtlasDescriptor));
        fontAssets = new FontAssets();
    }

    @Override
    public void dispose() {
        manager.get(textureAtlasDescriptor).dispose();
        manager.dispose();
        fontAssets.microsoftSans.dispose();
    }

    public SpriteAssets getSprites() {
        return sprites;
    }

    public FontAssets getFontAssets() {
        return fontAssets;
    }

    public static class SpriteAssets {
        public final TextureRegion background;
        public final TextureRegion lPiece;
        public final TextureRegion jPiece;
        public final TextureRegion iPiece;
        public final TextureRegion oPiece;
        public final TextureRegion tPiece;
        public final TextureRegion zPiece;
        public final TextureRegion sPiece;
        public final TextureRegion ghostPiece;
        public final TextureRegion wall;
        public final TextureRegion hudDisplayBackground;
        public final TextureRegion hudBackground;

        private SpriteAssets(TextureAtlas atlas) {
            lPiece = atlas.findRegion("lpiece");
            jPiece = atlas.findRegion("jpiece");
            iPiece = atlas.findRegion("ipiece");
            oPiece = atlas.findRegion("opiece");
            tPiece = atlas.findRegion("tpiece");
            zPiece = atlas.findRegion("zpiece");
            sPiece = atlas.findRegion("spiece");
            wall = atlas.findRegion("wall");
            ghostPiece = atlas.findRegion("ghost");
            background = atlas.findRegion("background");
            hudDisplayBackground = atlas.findRegion("hud_display");
            hudBackground = atlas.findRegion("hud_background");
        }
    }

    public static class FontAssets {
        public final BitmapFont microsoftSans;

        private FontAssets() {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("micross.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.borderColor = Color.BLACK;
            parameter.size = 80;
            parameter.borderWidth = 2;
            parameter.color = Color.YELLOW;
            microsoftSans = generator.generateFont(parameter);
            generator.dispose();
        }
    }
}
