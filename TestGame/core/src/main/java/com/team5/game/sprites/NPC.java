package com.team5.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.animation.Animator;
import com.team5.game.sprites.collisions.CharacterCollider;
import com.team5.game.sprites.pathfinding.NPCAIBehaviour;
import com.team5.game.sprites.pathfinding.Node;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.tools.Constants;

import java.util.Random;

public class NPC extends Sprite {

    /*
    NPC contains all of the information regarding an NPC
    in the game with reference to the AI used for it's pathfinding
     */

    //Collider
    public World world;
    public Body b2body;
    private final int size = Constants.TILE_SIZE;
    CharacterCollider charCollider = new CharacterCollider();

    //Animations
    Animator anim;

    public TextureRegion currentSprite;

    boolean facingRight;

    //Outlines
    PlayScreen screen;

    Animator outlineAnim;

    Image outlineImage;
    ImageButton outlineButton;

    //Movement
    public Vector2 direction;

    public float x;
    public float y;

    //AI
    NodeGraph graph;
    Node node;

    NPCAIBehaviour ai;

    //Audio
    Sound clicked = Gdx.audio.newSound(Gdx.files.internal("Audio/Sound Effects/wrong.wav"));

    public NPC(PlayScreen screen, World world, NodeGraph graph, Node node, Vector2 position){
        this.world = world;
        this.screen = screen;
        this.x = position.x;
        this.y = position.y;
        this.graph = graph;
        this.node = node;

        b2body = charCollider.defineCollider(world, position, size);

        setup();
    }

    //To be called every frame to move and animate the NPC.
    public void update(float delta){
        ai.update(delta);
        handleAnimations(direction);
    }

    //Sets up all the base Animations as well as the AI
    public void setup(){
        ai = new NPCAIBehaviour(this, graph, node);

        //Setting initial values of animations
        Random random = new Random();
        int sprite = random.nextInt(6);
        anim = new Animator("idle", "NPC/" + (sprite + 1) + "/Idle");
        anim.add("run", "NPC/" + (sprite + 1) + "/Run");
        facingRight = true;
        currentSprite = anim.getSprite();

        //Setting outline animations
        outlineAnim = new Animator("idle", "NPC/" + (sprite + 1) + "/IdleOutline");
        outlineAnim.add("run", "NPC/" + (sprite + 1) + "/RunOutline");

        outlineImage = new Image(outlineAnim.getSprite());
        outlineButton = new ImageButton(new Image(Constants.ATLAS.findRegion("Empty")).getDrawable());

        outlineButton.setPosition(x-4, y-4);
        outlineButton.setSize(Constants.TILE_SIZE+8, Constants.TILE_SIZE+8);

        outlineButton.getStyle().imageOver = outlineImage.getDrawable();

        outlineButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                clicked.play(0.3f);
                screen.gameController.getPlayer().health.decreaseHealth();
            }
        });

        screen.stage.addActor(outlineButton);
    }

    //Deciding which animation will be played each frame based on the ai movement
    void handleAnimations(Vector2 direction){
        if (direction.isZero(0.01f)){
            b2body.setLinearVelocity(0f, 0f);
            anim.play("idle");
            outlineAnim.play("idle");
        } else {
            b2body.setLinearVelocity(direction);
            anim.play("run");
            outlineAnim.play("run");
        }

        x = b2body.getPosition().x;
        y = b2body.getPosition().y;

        outlineButton.setPosition(x-4, y-4);

        currentSprite = anim.getSprite();
        outlineImage = new Image(outlineAnim.getSprite());
        outlineButton.getStyle().imageOver = outlineImage.getDrawable();

        if ((b2body.getLinearVelocity().x < 0 || !facingRight) && !anim.isFlipped()){
            anim.flip();
            outlineAnim.flip();
            facingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || facingRight) && anim.isFlipped()){
            anim.flip();
            outlineAnim.flip();
            facingRight = true;
        }
    }

    public void dispose(){
        clicked.dispose();
    }

}
