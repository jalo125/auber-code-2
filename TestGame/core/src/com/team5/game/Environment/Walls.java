package com.team5.game.Environment;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.team5.game.Tools.Constants;

public class Walls {

    /*
    Walls generates all of the walls in the map that the player
    can collide with using the Stop layer in the TileMap.
     */

    //References
    World world;
    TiledMap map;

    //Box2D
    Rectangle rect;
    Body body;
    BodyDef bodDef;
    PolygonShape shape;
    FixtureDef fixDef;

    //stopIndex is the index of the Stop object layer in the TileMap.
    int stopIndex = 5;

    public Walls(World world, TiledMap map){
        this.world = world;
        this.map = map;

        bodDef = new BodyDef();
        shape = new PolygonShape();
        fixDef = new FixtureDef();

        initialiseColliders();

    }

    //Loops through all rectangles on the Tilemap
    //object layer and adds a collider to them
    void initialiseColliders(){
        for(MapObject object: map.getLayers().get(stopIndex).getObjects().getByType(RectangleMapObject.class)){
            rect = ((RectangleMapObject) object).getRectangle();

            bodDef.type = BodyDef.BodyType.StaticBody;
            bodDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bodDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fixDef.shape = shape;

            fixDef.filter.groupIndex = Constants.GROUP_WALLS;

            body.createFixture(fixDef);
        }
    }

}
