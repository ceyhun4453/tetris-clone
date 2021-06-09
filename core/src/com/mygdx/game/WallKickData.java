package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

// Very simple class that holds data for wall kicks.
public class WallKickData {

    // Holds wall kick data for the pieces J, L, S, Z, T.
    private final ObjectMap<Rotation, Vector2[]> wallKicks;
    // Holds wall kick data for the piece I.
    private final ObjectMap<Rotation, Vector2[]> wallKicksForIPiece;

    public WallKickData() {
        wallKicks = new ObjectMap<>(8);
        wallKicksForIPiece = new ObjectMap<>(8);
        initMaps();
    }

    public void initMaps() {
        wallKicks.put(new Rotation(RotationState.SPAWN, RotationState.CLOCKWISE90), new Vector2[]{
                new Vector2(0, 0), new Vector2(-1, 0), new Vector2(-1, 1), new Vector2(0, -2), new Vector2(-1, -2)});
        wallKicks.put(new Rotation(RotationState.CLOCKWISE90, RotationState.SPAWN), new Vector2[]{
                new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, -1), new Vector2(0, 2), new Vector2(1, 2)});
        wallKicks.put(new Rotation(RotationState.CLOCKWISE90, RotationState.CLOCKWISE180), new Vector2[]{
                new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, -1), new Vector2(0, 2), new Vector2(1, 2)});
        wallKicks.put(new Rotation(RotationState.CLOCKWISE180, RotationState.CLOCKWISE90), new Vector2[]{
                new Vector2(0, 0), new Vector2(-1, 0), new Vector2(-1, 1), new Vector2(0, -2), new Vector2(-1, -2)});
        wallKicks.put(new Rotation(RotationState.CLOCKWISE180, RotationState.CLOCKWISE270), new Vector2[]{
                new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1), new Vector2(0, -2), new Vector2(1, -2)});
        wallKicks.put(new Rotation(RotationState.CLOCKWISE270, RotationState.CLOCKWISE180), new Vector2[]{
                new Vector2(0, 0), new Vector2(-1, 0), new Vector2(-1, -1), new Vector2(0, 2), new Vector2(-1, 2)});
        wallKicks.put(new Rotation(RotationState.CLOCKWISE270, RotationState.SPAWN), new Vector2[]{
                new Vector2(0, 0), new Vector2(-1, 0), new Vector2(-1, -1), new Vector2(0, 2), new Vector2(-1, 2)});
        wallKicks.put(new Rotation(RotationState.SPAWN, RotationState.CLOCKWISE270), new Vector2[]{
                new Vector2(0, 0), new Vector2(1, 0), new Vector2(1, 1), new Vector2(0, -2), new Vector2(1, -2)});

        wallKicksForIPiece.put(new Rotation(RotationState.SPAWN, RotationState.CLOCKWISE90), new Vector2[]{
                new Vector2(0, 0), new Vector2(-2, 0), new Vector2(1, 0), new Vector2(-2, -1), new Vector2(1, 2)});
        wallKicksForIPiece.put(new Rotation(RotationState.CLOCKWISE90, RotationState.SPAWN), new Vector2[]{
                new Vector2(0, 0), new Vector2(2, 0), new Vector2(-1, 0), new Vector2(2, 1), new Vector2(-1, -2)});
        wallKicksForIPiece.put(new Rotation(RotationState.CLOCKWISE90, RotationState.CLOCKWISE180), new Vector2[]{
                new Vector2(0, 0), new Vector2(-1, 0), new Vector2(2, 0), new Vector2(-1, 2), new Vector2(2, -1)});
        wallKicksForIPiece.put(new Rotation(RotationState.CLOCKWISE180, RotationState.CLOCKWISE90), new Vector2[]{
                new Vector2(0, 0), new Vector2(1, 0), new Vector2(-2, 0), new Vector2(1, -2), new Vector2(-2, 1)});
        wallKicksForIPiece.put(new Rotation(RotationState.CLOCKWISE180, RotationState.CLOCKWISE270), new Vector2[]{
                new Vector2(0, 0), new Vector2(2, 0), new Vector2(-1, 0), new Vector2(2, 1), new Vector2(-1, -2)});
        wallKicksForIPiece.put(new Rotation(RotationState.CLOCKWISE270, RotationState.CLOCKWISE180), new Vector2[]{
                new Vector2(0, 0), new Vector2(-2, 0), new Vector2(1, 0), new Vector2(-2, -1), new Vector2(1, 2)});
        wallKicksForIPiece.put(new Rotation(RotationState.CLOCKWISE270, RotationState.SPAWN), new Vector2[]{
                new Vector2(0, 0), new Vector2(1, 0), new Vector2(-2, 0), new Vector2(1, -2), new Vector2(-2, 1)});
        wallKicksForIPiece.put(new Rotation(RotationState.SPAWN, RotationState.CLOCKWISE270), new Vector2[]{
                new Vector2(0, 0), new Vector2(-1, 0), new Vector2(2, 0), new Vector2(-1, 2), new Vector2(2, -1)});
    }

    public Vector2[] getWallKickDataFor(Rotation rotation, Tetrimino tetrimino) {
        if (tetrimino.getTetrominoType() == Tetrimino.TetriminoType.I) {
            return wallKicksForIPiece.get(rotation);
        } else {
            return wallKicks.get(rotation);
        }
    }

}
