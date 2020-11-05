package com.team5.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team5.game.MainGame;
import com.team5.game.Sprites.Player;

public class PlayScreen implements Screen {

    private MainGame game;

    float speed = 100000;

    float x = 640;
    float y = 120;

    //anim controls what animation state the character is in
    int anim = 0;
    public static final float frameDuration = 0.2f;
    Animation<TextureRegion>[] animations;
    float stateTime;

    private boolean yInput;
    private boolean xInput;

    private OrthographicCamera cam;
    private Viewport port;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private TextureAtlas atlas;

    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;

    public PlayScreen(MainGame game){
        this.game = game;
        atlas = game.atlas;

        //Animations
        animations = new Animation[4];

        TextureRegion[][] spriteSheet = TextureRegion.split(new Texture("Test/SpriteSheet.png"), 32, 32);

        animations[0] = new Animation(frameDuration, spriteSheet[0]);
        animations[1] = new Animation(frameDuration/2, spriteSheet[1]);
        animations[2] = new Animation(frameDuration/2, spriteSheet[2]);
        animations[3] = new Animation(frameDuration, spriteSheet[3]);

        //Camera
        cam = new OrthographicCamera();
        port = new FitViewport(480, 270, cam);

        //Tilemap
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("TileMapTest.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //Collisions
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        player = new Player(world);
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

        game.batch.setProjectionMatrix(cam.combined);

        stateTime += delta;

        game.batch.begin();
        game.batch.draw(animations[anim].getKeyFrame(stateTime, true), x, y);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
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

    }

    //Past here is all the methods I made

    public void update(float delta){
        world.step(1/60f, 6, 2);

        //Moves the camera to the player
        cam.position.set(new Vector3(player.b2body.getPosition().x, player.b2body.getPosition().y, 0));

        checkInputs(Gdx.graphics.getDeltaTime());
        cam.update();
        renderer.setView(cam);
    }

    void checkInputs(float delta) {
        //Actual checking of inputs
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.UP)){
            yInput = false;
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0f);
        }else if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0, speed), player.b2body.getWorldCenter(), true);
            yInput = true;
        }else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -2){
            player.b2body.applyLinearImpulse(new Vector2(0, -speed), player.b2body.getWorldCenter(), true);
            yInput = true;
        }else if (!Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP)){
            yInput = false;
            player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xInput = false;
            player.b2body.setLinearVelocity(0f, player.b2body.getLinearVelocity().y);
        }else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2){
            player.b2body.applyLinearImpulse(new Vector2(-speed, 0), player.b2body.getWorldCenter(), true);
            xInput = true;
            anim = 2;
        }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(speed, 0), player.b2body.getWorldCenter(), true);
            xInput = true;
            anim = 1;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xInput = false;
            player.b2body.setLinearVelocity(0f, player.b2body.getLinearVelocity().y);
        }

        //Working out the animations from the input
        if (xInput == false && yInput == false){
            if (anim == 1){
                anim = 0;
            } else if (anim == 2){
                anim = 3;
            }
        } else if (yInput == true && xInput == false){
            if (anim == 0){
                anim = 1;
            } else if (anim == 3){
                anim = 2;
            }
        }

        x = player.b2body.getPosition().x;
        y = player.b2body.getPosition().y;
    }
}
