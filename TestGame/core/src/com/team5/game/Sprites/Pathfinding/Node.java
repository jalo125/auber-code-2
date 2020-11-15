package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class Node{

    /*
    Node represents any point that an NPC is going to use
    to guide their pathfinding around the map
     */

    float x;
    float y;
    String name;

    Vector2 dimensions;

    int index;

    public Node(float x, float y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
        this.dimensions = new Vector2(2, 2);
    }

    public Node(float x, float y, String name, Vector2 dimension){
        this.x = x;
        this.y = y;
        this.name = name;
        this.dimensions = dimension;
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

    public boolean equals(Node otherNode){
        return (name.equals(otherNode.getName()));
    }

    public Vector2 randomPos(){
        Random random = new Random();
        float randX = random.nextFloat();
        float randY = random.nextFloat();

        return new Vector2((randX-0.5f)*dimensions.x, (randY-0.5f)*dimensions.y);
    }
}
