package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    private static final AssetDescriptor<TextureAtlas> textureAtlasDescriptor =
            new AssetDescriptor<TextureAtlas>(Gdx.files.internal("tetris.atlas"), TextureAtlas.class);

    private AssetManager manager;
    public SpriteAssets sprites;

    public Assets(AssetManager manager) {
        this.manager = manager;
        this.manager.load(textureAtlasDescriptor);
        this.manager.finishLoading();
        sprites = new SpriteAssets(manager.get(textureAtlasDescriptor));
    }

    @Override
    public void dispose() {
        manager.get(textureAtlasDescriptor).dispose();
        manager.dispose();
    }

    public SpriteAssets getSprites() {
        return sprites;
    }

    public static class SpriteAssets {
        public final TextureRegion lPiece;
        public final TextureRegion jPiece;
        public final TextureRegion iPiece;
        public final TextureRegion oPiece;
        public final TextureRegion tPiece;
        public final TextureRegion zPiece;
        public final TextureRegion sPiece;

        private SpriteAssets(TextureAtlas atlas) {
            lPiece = atlas.findRegion("LPiece");
            jPiece = atlas.findRegion("JPiece");
            iPiece = atlas.findRegion("IPiece");
            oPiece = atlas.findRegion("OPiece");
            tPiece = atlas.findRegion("TPiece");
            zPiece = atlas.findRegion("ZPiece");
            sPiece = atlas.findRegion("SPiece");
        }
    }
}
