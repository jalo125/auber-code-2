package com.team5.game.tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;

import java.io.IOException;

public class LoadButtonClickListener extends ClickListener {

    private final GameStorage gameStorage;
    private final Sound click;
    private final MainGame game;
    private PlayScreen playScreen;

    public LoadButtonClickListener(final MainGame game, final GameStorage gameStorage, final Sound click) {
        super();
        this.game = game;
        this.gameStorage = gameStorage;
        this.click = click;
    }

    public void clicked(InputEvent event, float x, float y){
        click.play(0.5f, 1.5f, 0);
        try {
            GameState loadedGameState = gameStorage.load();
            GameState.initialise(loadedGameState);
            Difficulty.setDifficulty(GameState.getInstance().getDifficulty());
            if (playScreen == null) {
                playScreen = new PlayScreen(game);
            }
            game.setScreen(playScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // For test only
    public LoadButtonClickListener(final MainGame game, final PlayScreen playScreen, final GameStorage gameStorage, final Sound click) {
        this(game, gameStorage, click);
        this.playScreen = playScreen;
    }
}
