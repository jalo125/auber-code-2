package com.team5.game.Tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Environment.Brig;
import com.team5.game.Environment.SystemChecker;
import com.team5.game.MainGame;
import com.team5.game.Screens.LoseScreen;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Infiltrator;
import com.team5.game.Sprites.NPC;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.NodeGraph;
import com.team5.game.Sprites.Pathfinding.System;
import com.team5.game.Sprites.Player;
import com.team5.game.Sprites.Teleporters;

public class GameController {

    //References
    MainGame game;
    Player player;
    Teleporters teleporters;
    NodeGraph graph;
    Brig brig;
    SystemChecker systemChecker;

    Array<NPC> npcs;
    Array<Infiltrator> infiltrators;

    //NPC numbers
    int noNPCs = 72;
    int noInfiltrators = 8;

    public GameController(MainGame game, PlayScreen screen){
        this.game = game;

        //Player
        player = new Player(game, screen.getWorld());

        //Teleporters
        teleporters = new Teleporters(screen);

        //Checkers
        brig = new Brig();
        systemChecker = new SystemChecker();

        //NPCs
        graph = new NodeGraph();
        npcs = new Array<>();
        infiltrators = new Array<>();

        for (int i = 0; i < noNPCs; i++) {
            Node node = graph.getRandomRoom();
            NPC npc = new NPC(screen, screen.getWorld(), graph,
                    node, new Vector2(node.getX(), node.getY()));
            npcs.add(npc);
        }
        for (int i = 0; i < noInfiltrators; i++) {
            System node = graph.getRandomSystem();
            Infiltrator npc = new Infiltrator(game, screen, this, screen.getWorld(), graph,
                    node, new Vector2(node.getX(), node.getY()));
            infiltrators.add(npc);
        }
    }

    public void draw(SpriteBatch batch){

        graph.drawSystems(batch);

        for (NPC npc : npcs){
            batch.draw(npc.currentSprite, npc.x, npc.y);
        }
        for (Infiltrator bad : infiltrators){
            batch.draw(bad.currentSprite, bad.x, bad.y);
        }
    }

    public void drawPlayer(SpriteBatch batch){
        batch.draw(player.currentSprite, player.x, player.y);
    }

    public void update(float delta){
        checkSystems();

        //Moves player
        player.update();

        //Moves npc
        for (NPC npc : npcs){
            npc.update(delta);
        }
        for (Infiltrator bad : infiltrators){
            bad.update(delta);
        }
    }

    void checkSystems(){
        if (systemChecker.allSystemsBroken()){
            game.setScreen(new LoseScreen(game));
        }
    }

    public Player getPlayer(){
        return player;
    }

    public Teleporters getTeleporters(){
        return teleporters;
    }

    public SystemChecker getSystemChecker(){
        return systemChecker;
    }

    public Brig getBrig(){
        return brig;
    }

}
