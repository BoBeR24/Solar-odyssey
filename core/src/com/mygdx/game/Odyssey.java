package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

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
