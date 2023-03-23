package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Odyssey extends Game {
//	SpriteBatch batch;
	public ShapeRenderer shape;

	@Override
	public void create () {
//		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		this.setScreen(new SolarSystemScreen(this));
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
