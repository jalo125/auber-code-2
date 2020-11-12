package com.team5.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends Sprite {

    /*
    Player contains all of the information regarding
    the player as well as the methods used to control them,
    also their animations and sprite
     */

    public TextureAtlas atlas;

    //Collider
    public World world;
    public Body b2body;
    private int size = 16;
    private Box2DDebugRenderer b2dr;

    //Animations
    public static final float frameDuration = 0.2f;
    float stateTime;

    Animation<TextureRegion> currentAnim;
    Animation<TextureRegion> idleAnim;
    Animation<TextureRegion> runAnim;

    public TextureRegion currentSprite;

    boolean facingRight;

    //Inputs
    private int yInput;
    private int xInput;
    private Vector2 direction;


    //Movement
    float speed = 2000000;
    float maxSpeed = 4000000;

    public float x = 500;
    public float y = 320;

    public Player(World world, TextureAtlas atlas){
        this.world = world;
        this.atlas = atlas;
        definePlayer();
        setupAnimations();
    }

    public void Update(float delta){
        checkInputs(delta);
        handleAnimations(direction);
        stateTime += delta;
    }

    public void definePlayer(){
        BodyDef bodDef = new BodyDef();
        bodDef.position.set(x, y);
        bodDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodDef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f);
        shape.setPosition(new Vector2(size/2,size/2));
        fixDef.shape = shape;
        b2body.createFixture(fixDef);
    }

    public void setupAnimations(){
        //Setting initial values of animations
        idleAnim = new Animation<TextureRegion>(frameDuration, atlas.findRegions("Player/Idle"));
        runAnim = new Animation<TextureRegion>(frameDuration, atlas.findRegions("Player/Run"));
        currentAnim = idleAnim;
        facingRight = true;
        currentSprite = currentAnim.getKeyFrame(stateTime, true);
    }

    void checkInputs(float delta) {
        //Actual checking of inputs

        xInput = 0;
        yInput = 0;

        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
                && b2body.getLinearVelocity().y <= maxSpeed){
            yInput++;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
                && b2body.getLinearVelocity().y >= -maxSpeed) {
            yInput--;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                && b2body.getLinearVelocity().x >= -maxSpeed){
            xInput--;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
                && b2body.getLinearVelocity().x <= maxSpeed){
            xInput++;
        }

        direction = new Vector2(xInput * speed, yInput * speed);
    }

    void handleAnimations(Vector2 direction){
        //Deciding which animation will be played each frame
        if (direction.isZero(0.01f)){
            b2body.setLinearVelocity(0f, 0f);
            currentAnim = idleAnim;
        } else {
            b2body.applyLinearImpulse(direction, b2body.getWorldCenter(), true);
            currentAnim = runAnim;
        }

        x = b2body.getPosition().x;
        y = b2body.getPosition().y;

        currentSprite = currentAnim.getKeyFrame(stateTime, true);

        if ((b2body.getLinearVelocity().x < 0 || !facingRight) && !currentSprite.isFlipX()){
            currentSprite.flip(true, false);
            facingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || facingRight) && currentSprite.isFlipX()){
            currentSprite.flip(true, false);
            facingRight = true;
        }
    }

    public void updatePosition(Vector2 target){
        b2body.setTransform(target, 0);
        x = b2body.getPosition().x;
        y = b2body.getPosition().y;
    }

}
