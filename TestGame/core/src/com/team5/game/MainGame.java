package com.team5.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.team5.game.Screens.PlayScreen;

public class MainGame extends Game {

	public SpriteBatch batch;
	public TextureAtlas atlas;

	@Override
	public void create () {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("textures.atlas");
		this.setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose(){
		batch.dispose();
		atlas.dispose();
	}
}
