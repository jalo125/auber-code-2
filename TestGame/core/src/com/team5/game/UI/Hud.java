package com.team5.game.UI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Tools.Constants;
import com.team5.game.Tools.CustomCamera;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    public Camera cam;
    private PlayScreen screen;

    Image background;
    Vector2 backgroundPos = new Vector2(50 * Constants.TILE_SIZE, 95 * Constants.TILE_SIZE);

    TextureRegion fullHeart;
    TextureRegion emptyHeart;
    Array<Image> hearts;
    Vector2 heartPos = new Vector2(80, 100);
    float xOffset = 32;

    int maxHealth;
    int currentHealth;

    TextureAtlas atlas;

    public Hud(PlayScreen screen, SpriteBatch batch, TextureAtlas atlas, Stage stage){
        this.atlas = atlas;

        this.stage = stage;

        this.screen = screen;
    }

    void setupImages(){
        hearts = new Array<>();

        fullHeart = atlas.findRegion("Health/Heart");
        emptyHeart = atlas.findRegion("Health/Empty Heart");
        background = new Image(atlas.findRegion("Health/Bar"));

        background.setPosition(screen.camera.cam.position.x, screen.camera.cam.position.y);

//        for (int i =0; i < maxHealth; i++) {
//            Image newHeart = new Image(fullHeart);
//            newHeart.setPosition(heartPos.x + (xOffset*i), heartPos.y);
//
//            hearts.add(newHeart);
//            stage.addActor(newHeart);
//        }
    }

    public void update(){
        background.setPosition(screen.player.b2body.getPosition().x, screen.player.b2body.getPosition().y);
    }


}
