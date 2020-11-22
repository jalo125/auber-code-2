package com.team5.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.Environment.Brig;
import com.team5.game.MainGame;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Screens.WinScreen;
import com.team5.game.Sprites.Animation.Animator;
import com.team5.game.Sprites.Collisions.CharacterCollider;
import com.team5.game.Sprites.Pathfinding.AIBehaviour;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class Infiltrator extends NPC{

    /*
    Infiltrator contains all of the information regarding an Infiltrator
    in the game with reference to the AI used for it's pathfinding
     */

    MainGame game;

    boolean caught = false;

    public Infiltrator(MainGame game, PlayScreen screen, World world,
                       NodeGraph graph, Node node, Vector2 position) {
        super(screen, world, graph, node, position);
        this.game = game;
    }

    @Override
    public void update(float delta) {
        if (!caught) {
            super.update(delta);
        } else {
            super.handleAnimations(Vector2.Zero);
        }
    }

    @Override
    public void setupAnimations() {
        //Setting initial values of animations
        Random random = new Random();
        int sprite = random.nextInt(6);
        anim = new Animator("idle", "NPC/Infiltrator/Idle");
        anim.add("run", "NPC/Infiltrator/Run");
        facingRight = true;
        currentSprite = anim.getSprite();

        //Setting outline animations
        outlineAnim = new Animator("idle", "NPC/4/IdleOutline");
        outlineAnim.add("run", "NPC/4/RunOutline");

        outlineImage = new Image(outlineAnim.getSprite());
        outlineButton = new ImageButton(new Image(Constants.ATLAS.findRegion("Empty")).getDrawable());

        outlineButton.setPosition(x-4, y-4);
        outlineButton.setSize(Constants.TILE_SIZE+8, Constants.TILE_SIZE+8);

        outlineButton.getStyle().imageOver = outlineImage.getDrawable();

        outlineButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if (!caught) {
                    b2body.setTransform(screen.brig.imprison(), 0);
                    x = b2body.getPosition().x;
                    y = b2body.getPosition().y;
                    caught = true;
                    if (screen.brig.allCaught()) {
                        game.setScreen(new WinScreen(game));
                    }
                }
            }
        });

        screen.stage.addActor(outlineButton);
    }
}
