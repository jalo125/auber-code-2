package com.team5.game.tools;


import com.team5.game.sprites.Player;

public class GameStateUtils {

    public static void createGameState(Player player) {
        GameState gameState = GameState.getInstance();
        gameState.setPlayerX(player.getX());
        gameState.setPlayerY(player.getY());
        gameState.setCurrentHealth(player.getHealth());
    }

}
