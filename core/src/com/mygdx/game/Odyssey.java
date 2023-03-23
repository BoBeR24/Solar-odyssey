package com.mygdx.game;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Odyssey extends Game {
//	SpriteBatch batch;
	public ShapeRenderer shape;

	@Override
	public void create () {
//		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		try {
			this.setScreen(new SolarSystemScreen(this));
		} catch (IOException e) {
			System.err.println("Error ODS");
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		shape.dispose();
	}
}
