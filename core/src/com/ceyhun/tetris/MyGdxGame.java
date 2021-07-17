package com.ceyhun.tetris;
import com.badlogic.gdx.Game;
public class MyGdxGame extends Game {

	private TetrisGameScreen screen;

	@Override
	public void create () {
		screen = new TetrisGameScreen();
		setScreen(screen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}
}
