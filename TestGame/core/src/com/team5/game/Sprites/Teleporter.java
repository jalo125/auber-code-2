package com.team5.game.Sprites;

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
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.team5.game.Screens.PlayScreen;

import java.util.Random;

public class Teleporter {

    /*
    Teleporter sets up all of the teleporters using the TileMap
    to deduce where the teleporters are on the map and then
    check whether they're clicked and teleport accordingly.
     */

    //Screen Reference
    PlayScreen screen;

    //Tilemap
    Rectangle rect;
    Map map;

    //Images
    ImageButton teleport;
    Image teleOn;
    Image teleOff;
    Image outline;

    TextureAtlas atlas;

    //Position array
    Array<Vector2> telePositions;

    //Player variables
    int xOffset = 1;
    int yOffset = 0;

    int teleIndex = 5;

    public Teleporter(TiledMap map, PlayScreen screen, TextureAtlas atlas){
        this.atlas = atlas;
        this.map = map;
        this.screen = screen;

        teleOn = new Image(atlas.findRegion("Teleporter/On"));
        outline = new Image(atlas.findRegion("Teleporter/Outline"));
        telePositions = new Array<>();

        findPositions();
    }

    void findPositions(){
        for(MapObject object: map.getLayers().get(teleIndex).getObjects().getByType(RectangleMapObject.class)){
            rect = ((RectangleMapObject) object).getRectangle();

            teleport = new ImageButton(new Image(atlas.findRegion("Empty")).getDrawable());

            teleOff = new Image(atlas.findRegion("Teleporter/Off"));

            //The 8s are to make the hitbox bigger
            teleport.setPosition(rect.getX() - 4, rect.getY() - 4);
            teleport.setSize(rect.getWidth() + 8, rect.getHeight() + 8);

            teleOff.setPosition(rect.getX(), rect.getY());

            teleport.getStyle().imageOver = outline.getDrawable();

            telePositions.add(new Vector2(rect.getX(), rect.getY()));

            teleport.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    Random random = new Random();
                    int index = random.nextInt(telePositions.size);

                    screen.player.updatePosition(new Vector2(telePositions.get(index).x + (16 * xOffset),
                            telePositions.get(index).y + (16 * yOffset)));
                }
            });
            screen.teleStage.addActor(teleOff);
            screen.teleStage.addActor(teleport);
        }
    }

}
