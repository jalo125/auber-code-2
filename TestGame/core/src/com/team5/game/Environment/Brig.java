package com.team5.game.Environment;

import com.badlogic.gdx.math.Vector2;
import com.team5.game.Tools.Constants;

public class Brig {

    int xOffset = 3;

    Vector2 basePosition = new Vector2(7.5f, 94.5f);

    public int prisoners = 0;

    public Brig(){
    }

    public Vector2 imprison(){
        Vector2 position =
                new Vector2((basePosition.x + (xOffset*prisoners))
                        * Constants.TILE_SIZE, basePosition.y* Constants.TILE_SIZE);
        prisoners++;
        return position;
    }

    public boolean allCaught(){
        return prisoners>=8;
    }

}
