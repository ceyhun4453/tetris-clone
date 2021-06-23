package com.ceyhun.tetris.desktop;

import com.badlogic.gdx.math.Matrix3;

public class Test {
    public static void main(String[] args) {
        Matrix3 translationMatrix = new Matrix3(new float[] {1, 0, 0, 0, 1 ,0, -1.5f, -1.5f, 1});
        System.out.println(translationMatrix.inv());
    }
}
