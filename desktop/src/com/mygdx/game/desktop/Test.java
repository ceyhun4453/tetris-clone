package com.mygdx.game.desktop;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.RotationState;

public class Test {
    public static void main(String[] args) {
        Matrix3 translationMatrix = new Matrix3(new float[] {1, 0, 0, 0, 1 ,0, -1.5f, -1.5f, 1});
        System.out.println(translationMatrix.inv());
    }
}
