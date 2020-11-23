package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Environment.SystemChecker;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Infiltrator;
import com.team5.game.Sprites.NPC;

import java.util.Random;

public class InfiltratorAIBehaviour extends NPCAIBehaviour{

    System goalSystem;

    SystemChecker systemChecker;

    Array<System> systems;

    boolean breaking;

    float breakOdds = 1f;

    public InfiltratorAIBehaviour(PlayScreen screen, Infiltrator infiltrator, NodeGraph graph, Node node) {
        super(infiltrator, graph, node);
        systemChecker = screen.systemChecker;
        systems = new Array<>();
        systems.addAll(graph.getSystems());

        goalSystem = systems.random();
    }

    void newSystemTarget() {
        goalSystem = systems.random();
        goalNode = goalSystem;
        path = graph.findPath(currentNode, goalNode);

        target = path.get(currentIndex).randomPos();
    }

    @Override
    public void update(float delta) {
        if (waiting){
            wait(delta);
            npc.direction = Vector2.Zero;
        } else {
            npc.direction = move(npc.x, npc.y);
        }
    }

    @Override
    public void wait(float delta) {
         if (waitTime <= 0f || goalSystem.isBroken()){
             if (breaking && !goalSystem.isBroken()){
                 goalSystem.destroy();
                 systemChecker.breakSystem();
             }
            waiting = false;
            systems.removeValue(goalSystem, false);

            if (random.nextFloat() < breakOdds){
                breaking = true;
                newSystemTarget();
            } else {
                breaking = false;
                newTarget();
            }
        }else {
            waitTime -= delta;
        }
    }

    public boolean isBreaking(){
        return breaking;
    }

    public boolean isWaiting(){
        return waiting;
    }
}
