package com.team5.game.environment;

import com.badlogic.gdx.math.Vector2;
import com.team5.game.tools.Constants;
import com.team5.game.tools.Difficulty;

public class Brig {

    /*
    Brig is used to teleport the infiltrators to the brig when they're caught.
     */

    //The original position of the first caught infiltrator.
    Vector2 basePosition = new Vector2(7.5f, 94.5f);

    //The distance between each cell in the brig, so they fill in from the left.
    int xOffset = 3;

    private int prisoners = 0;

    private static final int MAX_INFILTRATOR_NUMBER = Difficulty.getNoInfiltrators();

    public Brig() {
    }

    //Called whenever an infiltrator is caught and moves them to the brig.
    public Vector2 imprison() {
        Vector2 position =
                new Vector2((basePosition.x + (xOffset * prisoners))
                        * Constants.TILE_SIZE, basePosition.y * Constants.TILE_SIZE);
        prisoners++;
        return position;
    }

    //Returns whether or not all the infiltrators have been caught.
    public boolean allCaught() {
        return prisoners >= MAX_INFILTRATOR_NUMBER;
    }

    int getPrisoners() {
        return prisoners;
    }
}
