package com.ceyhun.tetris;

import java.util.Random;

public class TetriminoGenerator {

    private Tetrimino.TetriminoType[] typeArray;
    private Random numberGenerator;
    private int pointer;

    public TetriminoGenerator() {
        numberGenerator = new Random();
        typeArray = Tetrimino.TetriminoType.values();
        shuffle(typeArray);
        pointer = 0;
    }

    public Tetrimino getRandomTetrimino() {
        Tetrimino t = Tetrimino.makeFrom(typeArray[pointer]);
        if (pointer == typeArray.length - 1) {
            pointer = 0;
            shuffle(typeArray);
        } else {
            pointer++;
        }

        return t;
    }

    private void shuffle(Tetrimino.TetriminoType[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = numberGenerator.nextInt(i + 1);
            Tetrimino.TetriminoType temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }
}
