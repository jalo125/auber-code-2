package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.team5.game.Sprites.NPC;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Sprites.Teleporter;
import com.team5.game.Tools.Camera;
import com.team5.game.Environment.Walls;
import com.team5.game.MainGame;
import com.team5.game.Sprites.Player;
import com.team5.game.Tools.NPCSpawner;

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
    public Stage teleStage;

    //References
    public Player player;
    private Walls walls;
    private Camera camera;
    private Teleporter teleporter;
    private NodeGraph graph;
    private NPC npcTest;

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
        player = new Player(world, atlas);

        //Camera setup
        camera = new Camera(player);

        //Teleporter setup
        teleStage = new Stage(camera.port);
        Gdx.input.setInputProcessor(teleStage);

        teleporter = new Teleporter(map, this, atlas);

        //Collisions for TileMap
        walls = new Walls(world, map);

        //NPCs
        graph = new NodeGraph();
        npcTest = new NPC(world, atlas, graph,
                graph.getNode(0), new Vector2(graph.getNode(0).getX(), graph.getNode(0).getY()));
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
        b2dr.render(world, new Matrix4(camera.cam.combined));

        game.batch.setProjectionMatrix(camera.cam.combined);

        game.batch.begin();
        game.batch.draw(player.currentSprite, player.x, player.y);
        game.batch.draw(npcTest.currentSprite, npcTest.x, npcTest.y);
        game.batch.end();

        teleStage.act(delta);
        teleStage.draw();
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

        //Moves the camera to the player
        camera.update();
        camera.follow(player);

        //Moves npc
        npcTest.update(delta);

        //Move player
        player.Update(delta);

        renderer.setView(camera.cam);
    }
}
