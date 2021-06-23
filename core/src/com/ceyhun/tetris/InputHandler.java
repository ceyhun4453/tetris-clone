package com.ceyhun.tetris;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.ObjectMap;

public class InputHandler implements InputProcessor {
    // Maps keys to commands.
    private ObjectMap<KeyEvent, Command> commands;
    // Maps keys to their state.

    public InputHandler() {
        commands = new ObjectMap<>(8);
    }

    public void registerCommand(int keyCode, KeyAction action, Command command) {
        commands.put(new KeyEvent(keyCode, action), command);
    }

    public void unregisterCommand(int keyCode, KeyAction action) {
        commands.remove(new KeyEvent(keyCode, action));
    }

    private static class KeyEvent {
        int keyCode;
        KeyAction action;

        public KeyEvent(int keyCode, KeyAction action) {
            this.keyCode = keyCode;
            this.action = action;
        }
    }

    public enum KeyAction {
        DOWN, UP;
    }

    @Override
    public boolean keyDown(int keycode) {
        KeyEvent keyEvent = new KeyEvent(keycode, KeyAction.DOWN);
        commands.get(keyEvent).execute();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        KeyEvent keyEvent = new KeyEvent(keycode, KeyAction.UP);
        commands.get(keyEvent).execute();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
