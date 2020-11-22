package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.team5.game.Tools.CustomCamera;
import com.team5.game.Environment.Walls;
import com.team5.game.MainGame;
import com.team5.game.Sprites.Player;
import com.team5.game.UI.Hud;
import com.team5.game.UI.Minimap.Minimap;
import com.team5.game.UI.PauseMenu;

public class PlayScreen implements Screen {

    /*
    PlayScreen is the class that renders the main gameplay scene
    of the game, taking all the components from other entities
     */

    //Game Reference
    private final MainGame game;

    //Tilemaps
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final TextureAtlas atlas;

    //Colliders
    private final World world;
    private final Box2DDebugRenderer b2dr;

    //Teleporters
    public Stage stage;

    //HUD
    private Hud hud;
    private PauseMenu pauseMenu;
    private Minimap minimap;

    public boolean paused;
    public boolean mapVisible;

    //References
    public Player player;
    private final Walls walls;
    public CustomCamera camera;
    private final Teleporters teleporters;
    private final NodeGraph graph;
    public Brig brig;
    public SystemChecker systemChecker;

    private final Array<NPC> npcs;
    private final Array<Infiltrator> infiltrators;

    public PlayScreen(MainGame game){
        this.game = game;
        atlas = game.atlas;

        //Tilemap
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("TileMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //Collisions
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        //Player setup
        player = new Player(game, world);

        //Camera setup
        camera = new CustomCamera(player);

        //UI setup
        stage = new Stage(camera.port);
        Gdx.input.setInputProcessor(stage);

        //Collisions for TileMap
        walls = new Walls(world, map);

        //Teleporter setup
        teleporters = new Teleporters(this);

        //NPCs
        graph = new NodeGraph();
        npcs = new Array<>();
        infiltrators = new Array<>();

        //Checkers
        brig = new Brig();
        systemChecker = new SystemChecker();

        for (int i = 0; i < 72; i++) {
            Node node = graph.getRandomRoom();
            int index = (i%(graph.getNodeCount()-1))+1;
            NPC npc = new NPC(this, world, graph,
                    node, new Vector2(node.getX(), node.getY()));
            npcs.add(npc);
        }
        for (int i = 0; i < 8; i++) {
            System node = graph.getRandomSystem();
            Infiltrator npc = new Infiltrator(game, this, world, graph,
                    node, new Vector2(node.getX(), node.getY()));
            infiltrators.add(npc);
        }

        //HUD
        hud = new Hud(this, atlas);
        pauseMenu = new PauseMenu(game, this);
        minimap = new Minimap(this, teleporters);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        checkPause();
        checkSystems();

        if (!paused && !mapVisible) {
            update(Gdx.graphics.getDeltaTime());
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //b2dr.render(world, new Matrix4(camera.cam.combined));

        game.batch.setProjectionMatrix(camera.cam.combined);

        game.batch.begin();
        graph.drawSystems(game.batch);

        for (NPC npc : npcs){
            game.batch.draw(npc.currentSprite, npc.x, npc.y);
        }
        for (Infiltrator bad : infiltrators){
            game.batch.draw(bad.currentSprite, bad.x, bad.y);
        }

        stage.act(delta);
        stage.draw();

        game.batch.draw(player.currentSprite, player.x, player.y);

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
        paused = true;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    //Past here is all the methods I made

    public void update(float delta){
        world.step(1/60f, 6, 2);

        //Move player
        player.update();

        //Moves the camera to the player
        camera.update();
        camera.follow(player);

        //Moves npc
        for (NPC npc : npcs){
            npc.update(delta);
        }
        for (Infiltrator bad : infiltrators){
            bad.update(delta);
        }

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
                Gdx.input.setInputProcessor(stage);
                paused = false;
            } else {
                pauseMenu.update();
                Gdx.input.setInputProcessor(pauseMenu.stage);
                paused = true;
            }
        }
    }

    void checkSystems(){
        if (systemChecker.allSystemsBroken()){
            game.setScreen(new LoseScreen(game));
        }
    }
}
