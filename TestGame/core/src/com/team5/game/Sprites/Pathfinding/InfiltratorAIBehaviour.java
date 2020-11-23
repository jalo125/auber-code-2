package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Environment.SystemChecker;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Infiltrator;
import com.team5.game.Tools.GameController;

public class InfiltratorAIBehaviour extends NPCAIBehaviour{

    /*
    InfiltratorAIBehaviour contains all of the basic AI for Infiltrators.

    It makes the Infiltrators either follow the same AI as the NPCs or
    target a system and break it.
     */

    //Systems
    System goalSystem;
    SystemChecker systemChecker;
    Array<System> systems;

    //States
    boolean breaking;

    float breakOdds = 1f;

    public InfiltratorAIBehaviour(GameController gameController, Infiltrator infiltrator, NodeGraph graph, Node node) {
        super(infiltrator, graph, node);
        systemChecker = gameController.getSystemChecker();
        systems = new Array<>();
        systems.addAll(graph.getSystems());

        goalSystem = systems.random();
    }

    //Used to randomly generate a target system from the systems that
    //haven't been visited yet.
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

    //wait is changed to break a system if they're at one.
    @Override
    public void wait(float delta) {
         if (waitTime <= 0f || goalSystem.getBroken()){
             if (breaking && !goalSystem.getBroken()){
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
