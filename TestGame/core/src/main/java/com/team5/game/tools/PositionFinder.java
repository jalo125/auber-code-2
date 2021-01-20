package com.team5.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Animation.AnimatedDrawable;
import com.team5.game.Tools.Constants;

import java.util.Random;

public class PositionFinder {

    /*
    PositionFinder is a tool that uses the TileMap to find the positions
    of all the colliders on an object layer, in terms of tiles in the Tilemap.

    I used it to find out the positions of the teleporters by creating a
    teleporter layer and adding collision squares where I wanted the teleporter
    to be, then this code finds the in-game positions.

    Saves time calculating in-game positions.
     */

    //Tilemap
    Rectangle rect;
    Map map;

    //index refers to the object layer index in the TileMap (the bottom layer being 0 going up)
    int index = 6;

    public PositionFinder(TiledMap map, PlayScreen screen){
        this.map = map;

        findPositions();
    }

    void findPositions(){
        for(MapObject object: map.getLayers().get(index).getObjects().getByType(RectangleMapObject.class)){
            rect = ((RectangleMapObject) object).getRectangle();

            //If you want it in terms of pixels remove the '/Constants.TILE_SIZE's
            Gdx.app.log("Position", String.valueOf(rect.getX()/Constants.TILE_SIZE)
                    + ", " + String.valueOf(rect.getY()/Constants.TILE_SIZE));
        }
    }

}
