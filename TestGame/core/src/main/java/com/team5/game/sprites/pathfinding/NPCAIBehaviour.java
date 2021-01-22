package com.team5.game.sprites.pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;
import com.team5.game.sprites.NPC;

import java.util.Random;

public class NPCAIBehaviour {

    /*
    NPCAIBehaviour contains all of the basic AI for NPCs.

    It makes the NPCs move to a random target destination and then stop
    for a random amount of time between given bounds, and repeat.
     */

    //NPC Reference
    NPC npc;

    //Movement
    int maxSpeed = 80;
    int minSpeed = 30;
    float speed;

    //Pathfinding
    NodeGraph graph;
    public Node currentNode;
    public Node goalNode;
    GraphPath<Node> path;
    int currentIndex;

    Vector2 target;
    float offset = 8;

    //Waiting
    float maxWait = 5;
    float minWait = 1;
    float waitTime;

    //Behaviours
    boolean waiting = false;

    Random random;

    public NPCAIBehaviour(NPC npc, NodeGraph graph, Node node){
        this.npc = npc;
        this.graph = graph;
        currentNode = node;

        random = new Random();
        speed = random.nextInt(maxSpeed - minSpeed);
        speed += minSpeed;

        newTarget();
    }

    //Is called to tell the NPC what to do on each frame.
    public void update(float delta){
        if (waiting){
            wait(delta);
            npc.direction = Vector2.Zero;
        } else {
            npc.direction = move(npc.x, npc.y);
        }
    }

    //Generates a random room for the npc to target.
    void newTarget(){
        goalNode = graph.getRandomRoom(currentNode);
        path = graph.findPath(currentNode, goalNode);

        target = path.get(currentIndex).randomPos();
    }

    //Moves the npc towards their target.
    public Vector2 move(float x, float y){
        if (goalNode.equals(path.get(currentIndex)) &&
                x < target.x + offset && x > target.x - offset &&
                y < target.y + offset && y > target.y - offset){
            currentNode = goalNode;
            currentIndex = 1;

            waiting = true;
            waitTime = (random.nextFloat()*(maxWait-minWait)) + minWait;

            return Vector2.Zero;

        } else if (x < target.x + offset && x > target.x - offset &&
                y < target.y + offset && y > target.y - offset){
            currentNode = path.get(currentIndex);
            currentIndex++;

            target = path.get(currentIndex).randomPos();
        }

        Vector2 resultant = new Vector2(target.x - x,
                target.y - y).nor();

        return new Vector2(resultant.x * speed, resultant.y * speed);
    }

    //Makes the npc wait for a certain amount of time
    public void wait(float delta){
        if (waitTime <= 0f){
            waiting = false;
            newTarget();
        }else {
            waitTime -= delta;
        }
    }

}
