package com.mygdx.game.GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Odyssey extends Game {
	public ShapeRenderer shape;

	@Override
	public void create () {
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
