package com.mygdx.game.GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Odyssey extends Game {
	public ShapeRenderer shape;
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2.0f);

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
