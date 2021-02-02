package com.team5.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.team5.game.screens.MainMenuScreen;
import com.team5.game.tools.Atlas;
import com.team5.game.tools.Constants;

public class MainGame extends Game {

	/*
	Main Game actually decides which screen will be shown at
	any given time.
	 */

    public SpriteBatch batch;
    public TextureAtlas atlas;

    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = Atlas.getInstance();
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}
