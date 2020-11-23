package com.team5.game.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.Environment.SystemChecker;
import com.team5.game.MainGame;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Screens.WinScreen;
import com.team5.game.Sprites.Animation.Animator;
import com.team5.game.Sprites.Pathfinding.*;
import com.team5.game.Sprites.Pathfinding.System;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class Infiltrator extends NPC{

    /*
    Infiltrator contains all of the information regarding an Infiltrator
    in the game with reference to the AI used for it's pathfinding
     */

    MainGame game;

    boolean caught = false;
    boolean imprisoned = false;

    float caughtWait;
    float timer;

    InfiltratorAIBehaviour ai;

    SystemChecker systemChecker;

    public Infiltrator(MainGame game, PlayScreen screen, World world,
                       NodeGraph graph, Node node, Vector2 position) {
        super(screen, world, graph, node, position);
        this.game = game;
        systemChecker = screen.systemChecker;

        ai = new InfiltratorAIBehaviour(screen, this, graph, node);
    }

    @Override
    public void update(float delta) {
        if (!caught) {
            ai.update(delta);
            if (ai.isWaiting() && ai.isBreaking()){
                anim.play("interact");
                outlineAnim.play("interact");

                currentSprite = anim.getSprite();
                outlineImage = new Image(outlineAnim.getSprite());
                outlineButton.getStyle().imageOver = outlineImage.getDrawable();
            } else {
                handleAnimations(direction);
            }

        } else {
            if (!imprisoned && caught && anim.finished()) {
                b2body.setTransform(screen.brig.imprison(), 0);
                x = b2body.getPosition().x;
                y = b2body.getPosition().y;
                if (screen.brig.allCaught()) {
                    game.setScreen(new WinScreen(game));
                }

                imprisoned = true;
            }
            currentSprite = anim.getSprite();
        }
    }

    @Override
    public void setup() {

        //Setting initial values of animations
        Random random = new Random();
        int sprite = random.nextInt(6)+1;
        anim = new Animator("idle", "NPC/" + sprite + "/Idle");
        anim.add("run", "NPC/" + sprite + "/Run");
        anim.add("interact", "NPC/" + sprite + "/Interact");
        anim.add("caught", "NPC/Infiltrator/Caught");
        facingRight = true;
        currentSprite = anim.getSprite();

        //Setting outline animations
        outlineAnim = new Animator("idle", "NPC/" + sprite + "/IdleOutline");
        outlineAnim.add("run", "NPC/" + sprite +"/RunOutline");
        outlineAnim.add("interact", "NPC/" + sprite + "/InteractOutline");

        outlineImage = new Image(outlineAnim.getSprite());
        outlineButton = new ImageButton(new Image(Constants.ATLAS.findRegion("Empty")).getDrawable());

        outlineButton.setPosition(x-4, y-4);
        outlineButton.setSize(Constants.TILE_SIZE+8, Constants.TILE_SIZE+8);

        outlineButton.getStyle().imageOver = outlineImage.getDrawable();

        outlineButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if (!caught) {
                    caught = true;
                    anim.play("caught");
                    outlineButton.getStyle().imageOver =
                            new Image(Constants.ATLAS.findRegion("Empty")).getDrawable();
                }
            }
        });

        screen.stage.addActor(outlineButton);
    }
}
