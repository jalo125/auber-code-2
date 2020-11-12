package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class Link<N> implements Connection<Node> {

    Node fromNode;
    Node toNode;
    float cost;

    public Link(Node fromNode, Node toNode){
        this.fromNode = fromNode;
        this.toNode = toNode;

        cost = Vector2.dst(fromNode.x, fromNode.y, toNode.x, toNode.y);
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}
