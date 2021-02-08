package com.team5.game.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    private static GameState instance;

    private int difficulty;
    private int currentHealth;
    private float playerX;
    private float playerY;

    private GameState() {}

    public static void initialise() {
        instance = new GameState();
        instance.setDifficulty(Difficulty.difficulty);
        instance.setCurrentHealth(Constants.MAX_HEALTH);
        instance.setPlayerX(50 * Constants.TILE_SIZE);
        instance.setPlayerY(95 * Constants.TILE_SIZE);
    }

    public static void initialise(GameState loadedGameState) {

        instance = loadedGameState;
    }

    public static GameState getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GameState has not been initialized");
        }
        return instance;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int healthRemaining) {
        this.currentHealth = healthRemaining;
    }

    public float getPlayerX() {
        return playerX;
    }

    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public void setPlayerY(float playerY) {
        this.playerY = playerY;
    }
}