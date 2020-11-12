package com.team5.game.Tools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Sprites.NPC;
import com.team5.game.Sprites.Pathfinding.Node;
import com.team5.game.Sprites.Pathfinding.Pathfinder;

public class NPCSpawner {

    public int noNPCs = 1;
    Array<NPC> NPCList = new Array<>();

    Pathfinder pathfinder;

    World world;

    TextureAtlas atlas;

    public NPCSpawner(World world, TextureAtlas atlas){
        this.world = world;
        this.atlas = atlas;

        pathfinder = new Pathfinder();

        for (int i = 0; i < noNPCs; i++){
            Node startNode = pathfinder.getNode(i);
            Vector2 startPos = new Vector2(startNode.getX(), startNode.getY());
            NPC newNPC = new NPC(world, atlas, pathfinder, startNode, startPos);

            NPCList.add(newNPC);
        }
    }

    public void update(float delta){
        for (NPC character : NPCList){
            character.update(delta);
        }
    }

    public NPC getNPC(int index){
        return NPCList.get(index);
    }

}
