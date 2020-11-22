package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Environment.Brig;
import com.team5.game.Sprites.Infiltrator;
import com.team5.game.Sprites.NPC;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Sprites.Teleporter;
import com.team5.game.Tools.Constants;
import com.team5.game.Tools.CustomCamera;
import com.team5.game.Environment.Walls;
import com.team5.game.MainGame;
import com.team5.game.Sprites.Player;

import java.lang.invoke.ConstantCallSite;
import java.util.Random;

public class PlayScreen implements Screen {

    /*
    PlayScreen is the class that renders the main gameplay scene
    of the game, taking all the components from other entities
     */

    //Game Reference
    private MainGame game;

    //Tilemaps
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TextureAtlas atlas;

    //Colliders
    private World world;
    private Box2DDebugRenderer b2dr;

    //Teleporters
    public Stage stage;

    //HUD
    Image healthBar;
    TextureRegion currentHealth;
    Vector2 healthOffset = new Vector2(16-Constants.CAMERA_WIDTH/2, Constants.CAMERA_HEIGHT/2-64);

    //References
    public Player player;
    private Walls walls;
    public CustomCamera camera;
    private Teleporter teleporter;
    private NodeGraph graph;
    public Brig brig;

    private Array<NPC> npcs;

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

        //Teleporter setup
        teleporter = new Teleporter(map, this);

        //Collisions for TileMap
        walls = new Walls(world, map);

        //NPCs
        graph = new NodeGraph();
        npcs = new Array<>();

        //Brig
        brig = new Brig();

        for (int i = 0; i < 72; i++) {
            Node node = graph.getRandomRoom();
            int index = (i%(graph.getNodeCount()-1))+1;
            NPC npc = new NPC(this, world, graph,
                    node, new Vector2(node.getX(), node.getY()));
            npcs.add(npc);
        }
        for (int i = 0; i < 8; i++) {
            Node node = graph.getRandomRoom();
            Infiltrator npc = new Infiltrator(game, this, world, graph,
                    node, new Vector2(node.getX(), node.getY()));
            npcs.add(npc);
        }

        //HUD
        currentHealth = atlas.findRegion("Health/3");
        healthBar = new Image(currentHealth);
        healthBar.setPosition(camera.cam.position.x + healthOffset.x,
                camera.cam.position.y + healthOffset.y);
        stage.addActor(healthBar);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

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

        stage.act(delta);

        stage.draw();

        game.batch.draw(player.currentSprite, player.x, player.y);

        game.batch.end();
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

        //HUD
        currentHealth = atlas.findRegion("Health/" + String.valueOf(player.getHealth()));
        healthBar.setPosition(camera.cam.position.x + healthOffset.x,
                camera.cam.position.y + healthOffset.y);
        healthBar.setDrawable(new Image(currentHealth).getDrawable());

        //Moves npc
        for (NPC npc : npcs){
            npc.update(delta);
        }

        renderer.setView(camera.cam);
    }
}
