package com.team5.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team5.game.Sprites.Animation.Animator;
import com.team5.game.Sprites.Collisions.CharacterCollider;
import com.team5.game.Tools.Constants;

public class Player extends Sprite {

    /*
    Player contains all of the information regarding
    the player as well as the methods used to control them,
    also their animations and sprite
     */

    //Collider
    public World world;
    public Body b2body;
    int size = 16;
    CharacterCollider charCollider = new CharacterCollider();

    //Animations
    Animator anim;

    public TextureRegion currentSprite;

    boolean facingRight;

    //Inputs
    private int yInput;
    private int xInput;
    private Vector2 direction;


    //Movement
    float speed = 2000000;

    public float x = 50 * Constants.TILE_SIZE;
    public float y = 95 * Constants.TILE_SIZE;

    public Player(World world, TextureAtlas atlas){
        this.world = world;

        b2body = charCollider.defineCollider(world, new Vector2(x, y), size);
        setupAnimations(atlas);
    }

    public void update(){
        checkInputs();
        handleAnimations(direction);
    }

    public void setupAnimations(TextureAtlas atlas){
        //Setting initial values of animations
        anim = new Animator(atlas, "idle", "Player/Idle");
        anim.add("run", "Player/Run");
        facingRight = true;
        currentSprite = anim.getSprite();
    }

    void checkInputs() {
        //Actual checking of inputs

        xInput = 0;
        yInput = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            yInput++;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            yInput--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            xInput--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            xInput++;
        }

        direction = new Vector2(xInput * speed, yInput * speed);
    }

    void handleAnimations(Vector2 direction){
        //Deciding which animation will be played each frame
        if (direction.isZero(0.01f)){
            b2body.setLinearVelocity(0f, 0f);
            anim.play("idle");
        } else {
            b2body.setLinearVelocity(direction.x, direction.y);
            anim.play("run");
        }

        x = b2body.getPosition().x;
        y = b2body.getPosition().y;

        currentSprite = anim.getSprite();

        if ((b2body.getLinearVelocity().x < 0 || !facingRight) && !anim.isFlipped()){
            anim.flip();
            facingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || facingRight) && anim.isFlipped()){
            anim.flip();
            facingRight = true;
        }
    }

    public void updatePosition(Vector2 target){
        b2body.setTransform(target, 0);
        x = b2body.getPosition().x;
        y = b2body.getPosition().y;
    }

}
