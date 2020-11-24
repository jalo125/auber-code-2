package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class Node{

    /*
    Node represents any point that an NPC is going to use
    to guide their pathfinding around the map.
     */

    //Positions
    float x;
    float y;

    //name is used for comparison and debugging, so I can see where the ai is going
    String name;

    //dimensions is used for randomly selecting a position to go to on the Node
    Vector2 dimensions;

    //Index in NodeGraph
    int index;

    Random random;

    public Node(float x, float y, String name){
        this(x, y, name, new Vector2(0.25f, 0.25f));
    }

    public Node(float x, float y, String name, float xDim, float yDim){
        this(x, y, name, new Vector2(xDim, yDim));
    }

    public Node(float x, float y, String name, Vector2 dimension){
        this.x = x*Constants.TILE_SIZE;
        this.y = y*Constants.TILE_SIZE;
        this.name = name;
        this.dimensions = new Vector2(dimension.x*Constants.TILE_SIZE,
                dimension.y*Constants.TILE_SIZE);

        random = new Random();
    }

    public void setIndex(int index){
        this.index = index;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public String getName() {
        return name;
    }

    //Allows you to compare nodes using their names
    public boolean equals(Node otherNode){
        return (name.equals(otherNode.getName()));
    }

    //Calculates a random position within the dimensions of this node
    public Vector2 randomPos(){
        float randX = random.nextFloat()-0.5f;
        float randY = random.nextFloat()-0.5f;

        return new Vector2(x + randX*dimensions.x,
                y + randY*dimensions.y);
    }
}
