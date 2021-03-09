package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import java.util.Objects;

// Class that represents each piece in a tetris game.
public class Tetrimino {

    private final int[][] arrangement;
    private final int constant;
    private final TetriminoType type;
    private RotationState rotationState;

    public static Tetrimino makeFrom(TetriminoType tetriminoType) {
        return new Tetrimino(tetriminoType.arrangement, tetriminoType.constant, tetriminoType);
    }

    public static Tetrimino getRandomTetromino() {
        return makeFrom(Objects.requireNonNull(
                TetriminoType.getTypeForConstant(MathUtils.random(TetriminoType.values().length - 1) + 1)));
    }

    private Tetrimino(int[][] arrangement, int constant, TetriminoType type) {
        this.arrangement = arrangement;
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
                {0, 4, 4,0},
                {0, 0, 0, 0}
        }, 4),
        T(new int[][]{
                {0, 0, 0},
                {5, 5, 5},
                {0, 5, 0}
        }, 5),
        S(new int[][]{
                {0, 0, 0},
                {0, 6, 6},
                {6, 6, 0}
        }, 6),
        Z(new int[][]{
                {0, 0, 0},
                {7, 7, 0},
                {0, 7, 7}
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
