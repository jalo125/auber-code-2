package com.team5.game.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.MainGame;
import com.team5.game.Screens.MainMenuScreen;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Player;
import com.team5.game.Tools.Constants;
import com.team5.game.Tools.CustomCamera;

public class PauseMenu {

    public MainGame game;

    public Stage stage;
    public CustomCamera camera;

    Image pauseImage;
    ImageButton menuButton;

    Vector2 pauseOffset = new Vector2(-112, -75);
    Vector2 menuOffset = new Vector2(-48, -48);

    public PauseMenu(MainGame game, PlayScreen screen){
        this.game = game;
        camera = screen.camera;

        setup();
    }

    void setup(){
        stage = new Stage(camera.port);
        pauseImage = new Image(new Texture("Sprites/Menu/Pause Menu.png"));
        pauseImage.setPosition(camera.cam.position.x + pauseOffset.x,
                camera.cam.position.y + pauseOffset.y);

        menuButton = new ImageButton(new Image(new Texture("Sprites/Menu/MenuOff.png")).getDrawable());
        menuButton.setPosition(camera.cam.position.x + menuOffset.x,
                camera.cam.position.y + menuOffset.y);
        menuButton.setSize(96, 32);
        menuButton.getStyle().imageOver = new Image(new Texture("Sprites/Menu/MenuOn.png")).getDrawable();

        menuButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(pauseImage);
        stage.addActor(menuButton);
    }

    public void update(){
        pauseImage.setPosition(camera.cam.position.x + pauseOffset.x,
                camera.cam.position.y + pauseOffset.y);
        menuButton.setPosition(camera.cam.position.x + menuOffset.x,
                camera.cam.position.y + menuOffset.y);
    }

    public void draw(float delta){
        stage.act(delta);
        stage.draw();
    }

}
