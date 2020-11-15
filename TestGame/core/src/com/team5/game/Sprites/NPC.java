package com.team5.game.Sprites;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.team5.game.Sprites.Animation.Animator;
import com.team5.game.Sprites.Collisions.CharacterCollider;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class NPC extends Sprite {

    /*
    NPC contains all of the information regarding an NPC
    in the game with reference to the AI used for it's pathfinding
     */

    //Collider
    public World world;
    public Body b2body;
    private int size = 16;
    CharacterCollider charCollider = new CharacterCollider();

    //Animations
    Animator anim;

    public TextureRegion currentSprite;

    boolean facingRight;

    //Movement
    float speed = 50;

    private Vector2 direction;

    public float x = 640;
    public float y = 120;

    //AI
    NodeGraph graph;

    public Node currentNode;
    public Node goalNode;

    GraphPath<Node> path;

    int currentIndex;

    public NPC(World world, TextureAtlas atlas, NodeGraph graph, Node node, Vector2 position){
        this.world = world;
        this.graph = graph;
        this.currentNode = node;
        this.x = position.x;
        this.y = position.y;

        newTarget();

        b2body = charCollider.defineCollider(world, position, size);
        setupAnimations(atlas);
    }

    public void update(float delta){
        AI();
        handleAnimations(direction);
    }

    public void setupAnimations(TextureAtlas atlas){
        //Setting initial values of animations
        Random random = new Random();
        int sprite = random.nextInt(6);
        anim = new Animator(atlas, "idle", "NPC/" + String.valueOf(sprite+1) + "/Idle");
        anim.add("run", "NPC/" + String.valueOf(sprite+1) + "/Run");
        facingRight = true;
        currentSprite = anim.getSprite();
    }

    void newTarget(){
        goalNode = graph.getRandom(currentNode);
        path = graph.findPath(currentNode, goalNode);
    }

    void AI(){
        if (x < goalNode.getX() + 8 && x > goalNode.getX() - 8 &&
            y < goalNode.getY() + 8 && y > goalNode.getY() - 8){
            currentNode = goalNode;
            currentIndex = 1;

            newTarget();

        } else if (x < path.get(currentIndex).getX() + 8 && x > path.get(currentIndex).getX() - 8 &&
                y < path.get(currentIndex).getY() + 8 && y > path.get(currentIndex).getY() - 8){
            currentNode = path.get(currentIndex);
            currentIndex++;
        }

        Vector2 resultant = new Vector2(path.get(currentIndex).getX() - x,
                path.get(currentIndex).getY() - y).nor();

        direction = new Vector2(resultant.x * speed, resultant.y * speed);
    }

    void handleAnimations(Vector2 direction){
        //Deciding which animation will be played each frame
        if (direction.isZero(0.01f)){
            b2body.setLinearVelocity(0f, 0f);
            anim.play("idle");
        } else {
            b2body.setLinearVelocity(direction);
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

}
