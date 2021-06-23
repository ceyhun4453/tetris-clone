package com.ceyhun.tetris;

import java.util.Arrays;

// Class that represents each piece in a tetris game.
public class Tetrimino {

    private final int[][] arrangement;
    private final int constant;
    private final TetriminoType type;
    private RotationState rotationState;

    public static Tetrimino makeFrom(TetriminoType tetriminoType) {
        return new Tetrimino(tetriminoType.arrangement, tetriminoType.constant, tetriminoType);
    }

    private Tetrimino(int[][] arrangement, int constant, TetriminoType type) {
        this.arrangement = new int[arrangement.length][arrangement[0].length];
        for (int i = 0; i < arrangement.length; i++) {
            this.arrangement[i] = Arrays.copyOf(arrangement[i], arrangement[i].length );
        }
        this.constant = constant;
        this.type = type;
        rotationState = RotationState.SPAWN;
    }

    public int getLength() {
        return arrangement.length;
    }

    public int getValue(int row, int col) {
        return arrangement[row][col];
    }

    public void setFull(int row, int col) {
        arrangement[row][col] = constant;
    }

    public void setEmpty(int row, int col) {
        arrangement[row][col] = 0;
    }

    public TetriminoType getTetrominoType() {
        return type;
    }

    public RotationState getRotationState() {
        return rotationState;
    }

    public void setRotationState(RotationState rotationState) {
        this.rotationState = rotationState;
    }

    public enum TetriminoType {

        I(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0}
        }, 1),
        L(new int[][]{
                {0, 0, 0},
                {2, 2, 2},
                {2, 0, 0}
        }, 2),
        J(new int[][]{
                {0, 0, 0},
                {3, 3, 3},
                {0, 0, 3}
        }, 3),
        O(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        }, 4),
        T(new int[][]{
                {0, 0, 0},
                {5, 5, 5},
                {0, 5, 0}
        }, 5),
        S(new int[][]{
                {0, 0, 0},
                {6, 6, 0},
                {0, 6, 6}
        }, 6),
        Z(new int[][]{
                {0, 0, 0},
                {0, 7, 7},
                {7, 7, 0}
        }, 7);



        private final int[][] arrangement;
        private final int constant;

        TetriminoType(int[][] arrangement, int constant) {
            this.arrangement = arrangement;
            this.constant = constant;
        }

        static TetriminoType getTypeForConstant(int constant) {
            switch (constant) {
                case 1: return I;
                case 2: return L;
                case 3: return J;
                case 4: return O;
                case 5: return T;
                case 6: return S;
                case 7: return Z;
            }
            return null;
        }
    }
}
