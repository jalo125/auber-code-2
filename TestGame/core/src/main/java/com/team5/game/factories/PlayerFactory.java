package com.team5.game.factories;

import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Player;

public class PlayerFactory {

    private final MainGame game;
    private final PlayScreen screen;

    public PlayerFactory(MainGame game, PlayScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    public Player create() {
        return new Player(game, screen.getWorld());
    }

}
