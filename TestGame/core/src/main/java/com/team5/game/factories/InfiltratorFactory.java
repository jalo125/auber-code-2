package com.team5.game.factories;

import com.badlogic.gdx.math.Vector2;
import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Infiltrator;
import com.team5.game.sprites.pathfinding.Node;
import com.team5.game.sprites.pathfinding.NodeGraph;
import com.team5.game.tools.GameController;

public class InfiltratorFactory {

    private final MainGame game;
    private final PlayScreen screen;

    public InfiltratorFactory(MainGame game, PlayScreen screen) {
        this.game = game;
        this.screen = screen;
    }

    public Infiltrator create(NodeGraph graph, GameController gameController, Node node, float x, float y, boolean imprisoned) {
        return new Infiltrator(game, screen, gameController, screen.getWorld(), graph,
                node, new Vector2(x, y), imprisoned);

    }
}
