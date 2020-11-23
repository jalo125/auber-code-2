package com.team5.game.UI.Minimap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team5.game.Screens.PlayScreen;
import com.team5.game.Sprites.Player;
import com.team5.game.Sprites.Teleporters;
import com.team5.game.Tools.Constants;

public class Point {

    /*
    Point is used for each point you can select on the teleporter
    Minimap.
     */

    PlayScreen screen;
    Stage stage;
    Teleporters teleporters;

    Player player;

    Image cursor;

    int offset = 13;

    ImageButton point;
    Vector2 position;
    String key;

    public Point(PlayScreen screen, Stage stage, Teleporters teleporters, String key, Vector2 position){
        this.screen = screen;
        this.stage = stage;
        this.teleporters = teleporters;
        this.key = key;
        this.position = position;

        player = screen.gameController.getPlayer();
        cursor = new Image(new Texture("Sprites/Minimap/Cursor.png"));

        setup();
    }

    void setup(){
        point = new ImageButton(new Image(Constants.ATLAS.findRegion("Empty")).getDrawable());

        //The 10s are to make the hitbox bigger
        point.setPosition(position.x - offset, position.y - offset);
        point.setSize(Constants.TILE_SIZE + 10, Constants.TILE_SIZE + 10);

        point.getStyle().imageOver = cursor.getDrawable();

        point.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Vector2 newPosition = teleporters.getTeleporter(key);
                player.updatePosition(new Vector2(newPosition.x -Constants.TILE_SIZE, newPosition.y));
                screen.minimapOff();
            }
        });
        stage.addActor(point);
    }

    //This is used to change it according to the cameras position whenever the minimap is called
    public void setPosition(float x, float y){
        point.setPosition(x + position.x - offset, y + position.y - offset);
    }

}
