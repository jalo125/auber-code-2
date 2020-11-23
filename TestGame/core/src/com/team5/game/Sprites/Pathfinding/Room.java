package com.team5.game.Sprites.Pathfinding;

public class Room extends Node{

    /*
    Room is used to differentiate hallways and rooms in the NodeGraph class.
     */

    public String tag;

    public Room(String tag, float x, float y, String name) {
        super(x, y, name);
        this.tag = tag;
    }

    public Room(String tag, float x, float y, String name, float xDim, float yDim) {
        super(x, y, name, xDim, yDim);
        this.tag = tag;
    }
}
