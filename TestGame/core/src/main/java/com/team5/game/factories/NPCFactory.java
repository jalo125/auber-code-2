package com.team5.game.factories;

import com.badlogic.gdx.math.Vector2;
import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.NPC;
import com.team5.game.sprites.pathfinding.Node;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.tools.GameController;

public class NPCFactory {

    private final MainGame game;
    private final PlayScreen screen;

    public NPCFactory(MainGame game, PlayScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    public NPC create(NodeGraph graph, GameController gameController, Node node, float x, float y) {
        return new NPC(screen, screen.getWorld(), graph, node, new Vector2(x, y));
    }
}
