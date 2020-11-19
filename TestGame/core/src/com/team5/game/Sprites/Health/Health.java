package com.team5.game.Sprites.Health;

import com.badlogic.gdx.Gdx;
import com.team5.game.MainGame;
import com.team5.game.Screens.LoseScreen;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Tools.Constants;

public class Health {

    MainGame game;

    int maxHealth;
    int currentHealth;

    public Health(MainGame game){
        this.game = game;

        maxHealth = Constants.MAX_HEALTH;
        currentHealth = maxHealth;
    }

    public int getHealth(){
        return currentHealth;
    }

    public void increaseHealth(){
        currentHealth++;
    }

    public void decreaseHealth(){
        currentHealth--;

        if(currentHealth <= 0) { game.setScreen(new LoseScreen(game)); }
    }

}
