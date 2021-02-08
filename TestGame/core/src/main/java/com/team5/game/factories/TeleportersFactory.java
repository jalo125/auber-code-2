package com.team5.game.factories;

import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Teleporters;

public class TeleportersFactory {

    private PlayScreen screen;

    public TeleportersFactory(PlayScreen screen) {
        this.screen = screen;
    }

    public Teleporters create() {
        return new Teleporters(screen);
    }
}
