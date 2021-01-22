package com.team5.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.team5.game.MainGame;
import com.team5.game.sprites.animation.Animator;
import com.team5.game.sprites.collisions.CharacterCollider;
import com.team5.game.sprites.health.Health;
import com.team5.game.tools.Constants;

public class Player extends Sprite {

    /*
    Player contains all of the information regarding
    the player as well as the methods used to control them.
     */

    //Collider
    public World world;
    public Body b2body;
    int size = Constants.TILE_SIZE;
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
    float speed = 150;

    public float x = 50 * Constants.TILE_SIZE;
    public float y = 95 * Constants.TILE_SIZE;

    //Health
    Health health;

    public Player(MainGame game, World world) {
        this.world = world;

        health = new Health(game, this);
        b2body = charCollider.defineCollider(world, new Vector2(x, y), size);
        setupAnimations();
    }

    //To be called every frame to move and animate the player.
    public void update() {
        handleAnimations(checkInputs());
        health.update();
    }

    //Setting up the animator as well as all the animations.
    public void setupAnimations() {
        anim = new Animator("idle", "Player/Idle");
        anim.add("run", "Player/Run");
        facingRight = true;
        currentSprite = anim.getSprite();
    }

    //Checks the keyboard inputs and produces a Vector2 accordingly
    Vector2 checkInputs() {
        xInput = 0;
        yInput = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            yInput++;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            yInput--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            xInput--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            xInput++;
        }

        return new Vector2(xInput * speed, yInput * speed);
    }

    //Deciding which animation will be played each frame based on input
    void handleAnimations(Vector2 direction) {
        if (direction.isZero(0.01f)) {
            b2body.setLinearVelocity(0f, 0f);
            anim.play("idle");
        } else {
            b2body.setLinearVelocity(direction.x, direction.y);
            anim.play("run");
        }

        x = b2body.getPosition().x;
        y = b2body.getPosition().y;

        currentSprite = anim.getSprite();

        if ((b2body.getLinearVelocity().x < 0 || !facingRight) && !anim.isFlipped()) {
            anim.flip();
            facingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || facingRight) && anim.isFlipped()) {
            anim.flip();
            facingRight = true;
        }
    }

    //Used to teleport the player across the map
    public void updatePosition(Vector2 target) {
        b2body.setTransform(target, 0);
        x = b2body.getPosition().x;
        y = b2body.getPosition().y;
    }

    public int getHealth() {
        return health.getHealth();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(currentSprite, x, y);
        if (health.getHealing()) {
            health.draw(batch, x - 2, y - 2);
        }
    }

}
