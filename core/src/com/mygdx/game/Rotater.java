package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;

public class Rotater {

    public static final int CLOCKWISE = 1;
    public static final int ANTICLOCKWISE = -1;

    private final WallKickData wallKickData;
    private final Matrix3 translation;
    private final Matrix3 invTranslation;
    private final Matrix3 clockwiseRotation;
    private final Matrix3 antiClockwiseRotation;

    public Rotater() {
        translation = new Matrix3(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1});
        invTranslation = new Matrix3(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1});
        clockwiseRotation = new Matrix3().setToRotation(-90);
        antiClockwiseRotation = new Matrix3().setToRotation(90);
        wallKickData = new WallKickData();
    }

    private final Matrix3 transformation = new Matrix3();
    private final Vector3 indexVector = new Vector3(0, 0, 1);
    private final int[][] tempArrangement = new int[4][4];

    // Clockwise rotation for positive values of "direction",
    // anticlockwise rotation for negative values of "direction"
    // If "direction" is equal to zero, returns the piece without rotating it.
    public Tetrimino rotate(Tetrimino piece, int direction) {
        resetTempArrangment();
        if (direction > 0) {
            setUpTransformationClockwise(piece);
            piece.setRotationState(RotationState.getClockwiseRotationState(piece.getRotationState()));
        } else if (direction < 0) {
            setUpTransformationAntiClockwise(piece);
            piece.setRotationState(RotationState.getAntiClockwiseRotationState(piece.getRotationState()));
        } else {
            return piece;
        }
        for (int r = 0; r < piece.getLength(); r++) {
            for (int c = 0; c < piece.getLength(); c++) {
                calculateRotatedPointAndWriteToArray(r, c, indexVector, tempArrangement, piece);
            }
        }
        copyTempArrangementToPiece(tempArrangement, piece);
        return piece;
    }

    Rotation rotation = new Rotation(true);

    public Tetrimino rotatePiece(Playfield field, Mover mover, int direction) {
        Tetrimino piece = field.getActivePiece();
        if (piece != null) {
            field.unmergeActivePiece();
            RotationState lastState = piece.getRotationState();
            rotate(piece, direction);
            RotationState nextState = piece.getRotationState();
            rotation.setStartState(lastState);
            rotation.setEndState(nextState);
            boolean isRotationPossible = false;
            for (Vector2 v : wallKickData.getWallKickDataFor(rotation, piece)) {
                int resultantRow = field.getActivePieceRow() + Math.round(v.y);
                int resultantCol = field.getActivePieceCol() + Math.round(v.x);
                if (field.isSpaceAvaiable(resultantRow, resultantCol, piece)) {
                    mover.movePiece(field, Math.round(v.x), Math.round(v.y));
                    isRotationPossible = true;
                    break;
                }
            }
            if (!isRotationPossible) {
                rotate(piece, -direction);
            }
            field.mergeActivePiece();
        }
        return piece;
    }

    private void calculateRotatedPointAndWriteToArray(int row, int col, Vector3 indexVector,
                                                      int[][] array, Tetrimino piece) {
        indexVector.y = row;
        indexVector.x = col;
        indexVector.mul(transformation);
        array[Math.round(indexVector.y)][Math.round(indexVector.x)] = piece.getValue(row, col);
    }

    private void resetTempArrangment() {
        for (int[] array : tempArrangement) {
            Arrays.fill(array, 0);
        }
    }

    private void copyTempArrangementToPiece(int[][] array, Tetrimino piece) {
        for (int i = 0; i < piece.getLength(); i++) {
            for (int j = 0; j < piece.getLength(); j++) {
                if (array[i][j] != 0) {
                    piece.setFull(i, j);
                } else {
                    piece.setEmpty(i, j);
                }
            }
        }
    }

    private void setUpTranslation(Tetrimino piece) {
        translation.val[6] = -(piece.getLength() - 1.0f) / 2.0f;
        invTranslation.val[6] = (piece.getLength() - 1.0f) / 2.0f;
        translation.val[7] = -(piece.getLength() - 1.0f) / 2.0f;
        invTranslation.val[7] = (piece.getLength() - 1.0f) / 2.0f;
    }

    private void setUpTransformationClockwise(Tetrimino piece) {
        setUpTranslation(piece);
        transformation.set(invTranslation).mul(clockwiseRotation).mul(translation);
    }

    private void setUpTransformationAntiClockwise(Tetrimino piece) {
        setUpTranslation(piece);
        transformation.set(invTranslation).mul(antiClockwiseRotation).mul(translation);
    }
}
