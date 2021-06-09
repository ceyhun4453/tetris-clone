package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.sun.tools.javac.util.Pair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClearerTest {

    private Clearer clearer;
    private Playfield mockedPlayfield;
    private Tetrimino mockedPiece;

    @Before
    public void reset() {
        clearer = new Clearer();
        mockedPlayfield = mock(Playfield.class);
        mockedPiece = mock(Tetrimino.class);
        when(mockedPlayfield.getActivePiece()).thenReturn(mockedPiece);
        when(mockedPlayfield.getPlayAreaHeight()).thenReturn(10);
        when(mockedPlayfield.getPlayAreaWidth()).thenReturn(10);
    }

    private void setUpMockPiece(Tetrimino.TetriminoType type, RotationState rotationState, int row, int col) {
        when(mockedPiece.getRotationState()).thenReturn(rotationState);
        when(mockedPiece.getTetrominoType()).thenReturn(type);
        when(mockedPlayfield.getActivePieceRow()).thenReturn(row);
        when(mockedPlayfield.getActivePieceCol()).thenReturn(col);
    }

    private void setUpTSpin() {
        Pair<Integer, Integer>[] minoRowColPairs = new Pair[]{new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3),
                new Pair<>(0, 4), new Pair<>(0, 5), new Pair<>(0, 6), new Pair<>(0, 7), new Pair<>(0, 8), new Pair<>(0, 9), new Pair<>(1, 3),
                new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(1, 7), new Pair<>(1, 8), new Pair<>(2, 6)};
        when(mockedPlayfield.getCellType(anyInt(), anyInt())).thenAnswer((Answer<CellType>) invocation -> {
            int row = invocation.getArgument(0);
            int col = invocation.getArgument(1);
            if (Arrays.asList(minoRowColPairs).contains(new Pair<>(row, col))) {
                return CellType.TETRIMINO;
            }
            return CellType.EMPTY;
        });
        setUpMockPiece(Tetrimino.TetriminoType.T, RotationState.CLOCKWISE180, 0, 4);
    }

    @Test
    public void testTSpin() {
        setUpTSpin();
        Clearer.Clear clear = clearer.clearFullRows(mockedPlayfield, new Movement(new Vector2(5, 1), new Vector2(5, 1),
                RotationState.CLOCKWISE270, RotationState.CLOCKWISE180));
        assertThat(clear.getNumberOfLines(), is(1));
        assertThat(clear.getClearType(), is(Clearer.ClearType.TSpin));
    }

    private void setUpMiniTspin() {
        Pair<Integer, Integer>[] minoRowColPairs = new Pair[]{new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3),
                new Pair<>(0, 4), new Pair<>(0, 5), new Pair<>(0, 6), new Pair<>(0, 7), new Pair<>(0, 8), new Pair<>(1, 0), new Pair<>(1, 1),
                new Pair<>(1, 2), new Pair<>(1, 3), new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(1, 7), new Pair<>(1, 8),
                new Pair<>(1, 9), new Pair<>(2, 4), new Pair<>(2, 5), new Pair<>(2, 6), new Pair<>(2, 8), new Pair<>(2, 9)};
        when(mockedPlayfield.getCellType(anyInt(), anyInt())).thenAnswer(new Answer<CellType>() {
            @Override
            public CellType answer(InvocationOnMock invocation) throws Throwable {
                int row = invocation.getArgument(0);
                int col = invocation.getArgument(1);
                if (Arrays.asList(minoRowColPairs).contains(new Pair<>(row, col))) {
                    return CellType.TETRIMINO;
                } else {
                    return CellType.EMPTY;
                }
            }
        });
        setUpMockPiece(Tetrimino.TetriminoType.T, RotationState.SPAWN, 0, 5);
    }

    @Test
    public void testMiniTSpin() {
        setUpMiniTspin();
        Clearer.Clear clear = clearer.clearFullRows(mockedPlayfield, new Movement(new Vector2(6, 1), new Vector2(5, 0),
                RotationState.CLOCKWISE270, RotationState.SPAWN));
        assertThat(clear.getNumberOfLines(), is(1));
        assertThat(clear.getClearType(), is(Clearer.ClearType.MiniTSpin));
    }

    private void setUpTSpinDouble() {
        Pair<Integer, Integer>[] minoRowColPairs = new Pair[]{new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3),
                new Pair<>(0, 4), new Pair<>(0, 5), new Pair<>(0, 6), new Pair<>(0, 9), new Pair<>(1, 0), new Pair<>(1, 1), new Pair<>(1, 2),
                new Pair<>(1, 3), new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(1, 7), new Pair<>(1, 8), new Pair<>(1, 9),
                new Pair<>(2, 0), new Pair<>(2, 1), new Pair<>(2, 2), new Pair<>(2, 3), new Pair<>(2, 4), new Pair<>(2, 5), new Pair<>(2, 6),
                new Pair<>(2, 7), new Pair<>(2, 8), new Pair<>(2, 9), };
        when(mockedPlayfield.getCellType(anyInt(), anyInt())).thenAnswer(new Answer<CellType>() {
            @Override
            public CellType answer(InvocationOnMock invocation) throws Throwable {
                int row = invocation.getArgument(0);
                int col = invocation.getArgument(1);
                if (Arrays.asList(minoRowColPairs).contains(new Pair<>(row, col))) {
                    return CellType.TETRIMINO;
                } else {
                    return CellType.EMPTY;
                }
            }
        });
        setUpMockPiece(Tetrimino.TetriminoType.T, RotationState.CLOCKWISE270, 0, 7);
    }

    @Test
    public void testTSpinDouble() {
        setUpTSpinDouble();
        Clearer.Clear clear = clearer.clearFullRows(mockedPlayfield, new Movement(new Vector2(6, 2), new Vector2(7, 0),
                RotationState.SPAWN, RotationState.CLOCKWISE270));
        assertThat(clear.getNumberOfLines(), is(2));
        assertThat(clear.getClearType(), is(Clearer.ClearType.TSpin));
    }
}