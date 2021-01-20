package com.team5.game.UI;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Player;
import com.team5.game.Tools.Constants;
import com.team5.game.Tools.CustomCamera;

public class Hud {

    /*
    Hud creates just the healthbar at the moment and
    creates a new stage to place it on in PlayScreen.
     */

    public Stage stage;
    public CustomCamera camera;

    public Player player;

    Image healthBar;
    TextureRegion currentHealth;
    Vector2 healthOffset = new Vector2(16-Constants.CAMERA_WIDTH/2, Constants.CAMERA_HEIGHT/2-48);

    TextureAtlas atlas = Constants.ATLAS;

    public Hud(PlayScreen screen){
        player = screen.gameController.getPlayer();
        camera = screen.camera;

        setupImages();
    }

    void setupImages(){
        stage = new Stage(camera.port);

        currentHealth = atlas.findRegion("Health/3");
        healthBar = new Image(currentHealth);
        healthBar.setPosition(camera.cam.position.x + healthOffset.x,
                camera.cam.position.y + healthOffset.y);
        stage.addActor(healthBar);
    }

    public void update(){
        currentHealth = atlas.findRegion("Health/" + player.getHealth());
        healthBar.setPosition(camera.cam.position.x + healthOffset.x,
                camera.cam.position.y + healthOffset.y);
        healthBar.setDrawable(new Image(currentHealth).getDrawable());
    }

    public void draw(float delta){
        stage.act(delta);
        stage.draw();
    }

}
