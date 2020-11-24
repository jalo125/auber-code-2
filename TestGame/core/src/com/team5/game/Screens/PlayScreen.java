package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Environment.Brig;
import com.team5.game.Environment.SystemChecker;
import com.team5.game.Sprites.Infiltrator;
import com.team5.game.Sprites.NPC;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Sprites.Pathfinding.System;
import com.team5.game.Sprites.Teleporters;
import com.team5.game.Tools.Constants;
import com.team5.game.Tools.CustomCamera;
import com.team5.game.Environment.Walls;
import com.team5.game.MainGame;
import com.team5.game.Sprites.Player;
import com.team5.game.Tools.GameController;
import com.team5.game.UI.Hud;
import com.team5.game.UI.Minimap.Minimap;
import com.team5.game.UI.PauseMenu;

public class PlayScreen implements Screen {

    /*
    PlayScreen is the class that renders the main gameplay scene
    of the game, taking all the components from other entities.
     */

    //Game Reference
    private final MainGame game;

    //Tilemaps
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    //Colliders
    private final World world;
    private final Box2DDebugRenderer b2dr;

    //Stage
    public Stage stage;

    //HUD
    private Hud hud;
    private PauseMenu pauseMenu;
    private Minimap minimap;

    public boolean paused;
    public boolean mapVisible;

    //Audio
    Music music = Gdx.audio.newMusic(Gdx.files.internal("Audio/Music/song.wav"));

    float volume = 0.01f;

    //References
    private final Walls walls;
    public CustomCamera camera;
    public GameController gameController;

    public PlayScreen(MainGame game){
        this.game = game;

        //Tilemap
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("TileMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //Collisions
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        //Camera setup
        camera = new CustomCamera();

        //UI setup
        stage = new Stage(camera.port);
        Gdx.input.setInputProcessor(stage);

        //Game Controller
        gameController = new GameController(game, this);
        camera.follow(gameController.getPlayer());

        //Collisions for TileMap
        walls = new Walls(world, map);

        //HUD
        hud = new Hud(this);
        pauseMenu = new PauseMenu(game, this);
        minimap = new Minimap(this, gameController.getTeleporters());

        //Audio
        music.setLooping(true);
        music.setVolume(volume);
        music.play();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        checkPause();

        if (!paused && !mapVisible) {
            update(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //Use b2dr.render(world, new Matrix4(camera.cam.combined)); to check collision boxes

        game.batch.setProjectionMatrix(camera.cam.combined);

        game.batch.begin();
        gameController.draw(game.batch);
        stage.act(delta);
        stage.draw();
        gameController.drawPlayer(game.batch);
        game.batch.end();

        hud.draw(delta);

        if (paused) {
            pauseMenu.draw(delta);
        }

        if (mapVisible){
            minimap.draw(delta);
        }

    }

    @Override
    public void resize(int width, int height) {
        camera.port.update(width, height);
    }

    @Override
    public void pause() {
        pauseMenu.update();
        Gdx.input.setInputProcessor(pauseMenu.stage);
        music.pause();
        paused = true;
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
        this.dispose();
        stage.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        minimap.dispose();
        gameController.dispose();
        music.dispose();
    }

    //Past here is all the methods I made

    public void update(float delta){
        world.step(1/60f, 6, 2);

        gameController.update(delta);

        //Moves the camera to the player
        camera.update();
        camera.follow(gameController.getPlayer());

        //HUD
        hud.update();

        renderer.setView(camera.cam);
    }

    public void minimapOn(){
        minimap.update();
        Gdx.input.setInputProcessor(minimap.stage);
        mapVisible = true;
    }

    public void minimapOff(){
        Gdx.input.setInputProcessor(stage);
        mapVisible = false;
    }

    void checkPause(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if (mapVisible){
                minimapOff();
            } else if (paused){
                music.play();
                Gdx.input.setInputProcessor(stage);
                paused = false;
            } else {
                music.pause();
                pauseMenu.update();
                Gdx.input.setInputProcessor(pauseMenu.stage);
                paused = true;
            }
        }
    }

    public World getWorld(){return world;}
}
