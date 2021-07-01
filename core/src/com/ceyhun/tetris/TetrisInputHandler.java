package com.ceyhun.tetris;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;

public class TetrisInputHandler extends InputAdapter {
    public static final int KEY_DOWN = 1;
    public static final int KEY_UP = 2;
    public static final int KEY_HELD = 3;

    private IntArray keyHeldArray;
    private IntMap<Command> keyHeldCommands;
    private IntMap<Command> keyDownCommands;
    private IntMap<Command> keyUpCommands;

    public TetrisInputHandler() {
        keyHeldArray = new IntArray(8);
        keyHeldCommands = new IntMap<>(8);
        keyDownCommands = new IntMap<>(8);
        keyUpCommands = new IntMap<>(8);
    }

    public void processHeldInputs() {
        for (int i = 0; i < keyHeldArray.size; i++) {
            Command c = keyHeldCommands.get(keyHeldArray.get(i));
            if (c != null) {
                c.execute();
            }
        }
    }

    public void registerCommand(int keyCode, int eventType, Command command) {
        switch (eventType) {
            case KEY_DOWN:
                keyDownCommands.put(keyCode, command);
                break;
            case KEY_UP:
                keyUpCommands.put(keyCode, command);
                break;
            case KEY_HELD:
                keyHeldCommands.put(keyCode, command);
                break;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        keyHeldArray.add(keycode);
        Command command = keyDownCommands.get(keycode);
        if (command != null) {
            command.execute();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyHeldArray.removeValue(keycode);
        Command command = keyUpCommands.get(keycode);
        if (command != null) {
            command.execute();
        }
        return true;
    }
}
