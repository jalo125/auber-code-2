package com.team5.game.Sprites.Pathfinding;

public class Node{

    float x;
    float y;
    String name;

    int index;

    public Node(float x, float y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
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
}
