package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.sun.tools.javac.util.Pair;
import org.junit.Before;
import org.junit.Test;
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
    private Tetrimino mockedT;

    @Before
    public void reset() {
        clearer = new Clearer();
        mockedPlayfield = mock(Playfield.class);
        mockedT = mock(Tetrimino.class);
    }

    public void setUpClearFullRowsTSpin() {
        clearer = new Clearer();
        mockedPlayfield = mock(Playfield.class);
        Pair<Integer, Integer>[] minoRowColPairs = new Pair[] {new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3),
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

        mockedT = mock(Tetrimino.class);
        when(mockedT.getTetrominoType()).thenReturn(Tetrimino.TetriminoType.T);
        when(mockedT.getRotationState()).thenReturn(RotationState.CLOCKWISE180);
        when(mockedPlayfield.getActivePiece()).thenReturn(mockedT);
        when(mockedPlayfield.getPlayAreaHeight()).thenReturn(10);
        when(mockedPlayfield.getPlayAreaWidth()).thenReturn(10);
        when(mockedPlayfield.getActivePieceCol()).thenReturn(4);
        when(mockedPlayfield.getActivePieceRow()).thenReturn(0);
    }

    @Test
    public void clearFullRowsTSpin() {
        setUpClearFullRowsTSpin();
        Clearer.Clear clear = clearer.clearFullRows(mockedPlayfield, new Movement(new Vector2(5, 1), new Vector2(5, 1),
                RotationState.CLOCKWISE270, RotationState.CLOCKWISE180));
        assertThat(clear.getNumberOfLines(), is(1));
        assertThat(clear.getClearType(), is(Clearer.ClearType.TSpin));
    }
}