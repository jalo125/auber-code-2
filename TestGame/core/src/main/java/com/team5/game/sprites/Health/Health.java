package com.team5.game.Sprites.Health;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.team5.game.MainGame;
import com.team5.game.Screens.LoseScreen;
import com.team5.game.Sprites.Animation.Animator;
import com.team5.game.Sprites.Player;
import com.team5.game.Tools.Constants;

public class Health {

    /*
    Health contains the health methods for the player.
     */

    //References
    MainGame game;
    Player player;

    //Health integers
    int maxHealth;
    int currentHealth;

    //Regaining health
    Animator anim;
    TextureRegion healEffect;

    float timeToHeal = 3;
    float timer;

    boolean healing = false;

    //These are the bounds for the infirmary
    Vector2 upperBounds = new Vector2(92*Constants.TILE_SIZE, 96*Constants.TILE_SIZE);
    Vector2 lowerBounds = new Vector2(71*Constants.TILE_SIZE, 82*Constants.TILE_SIZE);

    public Health(MainGame game, Player player){
        this.game = game;
        this.player = player;

        maxHealth = Constants.MAX_HEALTH;
        currentHealth = maxHealth;

        anim = new Animator("idle", "Player/HealEffect");
        anim.play("idle");
        healEffect = anim.getSprite();
    }

    //Called every frame, if the player is in the infirmary it's healed every timeToHeal seconds.
    public void update(){
        healEffect = anim.getSprite();
        if (player.x >= lowerBounds.x && player.x <= upperBounds.x &&
        player.y >= lowerBounds.y && player.y <= upperBounds.y && currentHealth < maxHealth){
            if (!healing){
                timer = 0;
                healing = true;
            } else{
                timer += Gdx.graphics.getDeltaTime();
                if (timer >= timeToHeal){
                    increaseHealth();
                    timer = 0;
                }
            }
        } else {
            healing = false;
        }
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

    public boolean getHealing(){return healing;}

    //Draws the Healing Effect
    public void draw(SpriteBatch batch, float x, float y){
        batch.draw(healEffect, x, y);
    }
}
