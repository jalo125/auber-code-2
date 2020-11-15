package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class NodeHeuristic implements Heuristic<Node> {

    /*
    NodeHeuristic is just the heuristic for the Nodes used
    in the NodeGraph class when performing an A* search to
    find a path
     */

    @Override
    public float estimate(Node currentNode, Node goalNode) {
        return Vector2.dst(currentNode.x, currentNode.y, goalNode.x, goalNode.y);
    }
}
