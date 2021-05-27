package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Movement {
    private Translation translation;
    private Rotation rotation;

    public Movement(Translation translation, Rotation rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public Translation getTranslation() {
        return translation;
    }

    public Rotation getRotation() {
        return rotation;
    }
}
