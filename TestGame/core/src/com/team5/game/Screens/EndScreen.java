package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.team5.game.MainGame;
import com.team5.game.Tools.CustomCamera;

public class EndScreen implements Screen {

    /*
    EndScreen is the class that sets up both the ending menu
    screens, since they have similar code.
     */

    //Main Game Reference
    MainGame game;

    //Stage
    Stage stage;

    //Title
    Texture title;
    Vector2 titlePosition;

    //Reference
    private final CustomCamera camera;
    World world;

    public EndScreen (final MainGame game){
        this.game = game;

        //Camera
        camera = new CustomCamera();

        //World
        world = new World(new Vector2(0, 0), true);

        //Stage
        stage = new Stage(camera.port);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.step(1/60f, 6, 2);

        //Updates Camera
        camera.update();

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.cam.combined);

        game.batch.begin();
        game.batch.draw(title, titlePosition.x, titlePosition.y);
        game.batch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        camera.port.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.dispose();
        stage.dispose();
    }

    //Custom functions from here

    public void setTitle(String path, Vector2 position){
        title = new Texture(path);
        titlePosition = position;
    }

    //Adds a button, nextScreen being the screen it transfers to when clicked
    public void addButton(final Screen nextScreen, Vector2 position, Vector2 size,
                          String offTexture, String onTexture){
        ImageButton button = new ImageButton(new Image(new Texture(offTexture)).getDrawable());

        button.setPosition(position.x, position.y);
        button.setSize(size.x, size.y);

        button.getStyle().imageOver = new Image(new Texture(onTexture)).getDrawable();
        button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(nextScreen);
            }
        });

        stage.addActor(button);
    }

}
