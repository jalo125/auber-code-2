package com.team5.game.ui.minimap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Teleporters;
import com.team5.game.tools.CustomCamera;

public class Minimap {

    /*
    Minimap contains all the methods used to create the teleporter minimap.
     */

    public PlayScreen screen;
    public Stage stage;
    public CustomCamera camera;

    Teleporters teleporters;

    Image minimap;
    Image cursor;

    Vector2 mapOffset = new Vector2(-160, -100);

    //Point positions
    Vector2 PQPosition = new Vector2(93, 119);
    Vector2 enginePosition = new Vector2(240, 48);
    Vector2 infirmaryPosition = new Vector2(261, 169);
    Vector2 brigPosition = new Vector2(103, 171);
    Vector2 northWingPosition = new Vector2(167, 150);

    Array<Point> points;

    public Minimap(PlayScreen screen, Teleporters teleporters) {
        this.screen = screen;
        this.teleporters = teleporters;
        camera = screen.camera;

        setup();
    }

    void setup() {
        stage = new Stage(camera.port);
        points = new Array<>();

        minimap = new Image(new Texture("Sprites/Minimap/Map.png"));
        minimap.setPosition(camera.cam.position.x + mapOffset.x,
                camera.cam.position.y + mapOffset.y);

        stage.addActor(minimap);

        cursor = new Image(new Texture("Sprites/Minimap/Cursor.png"));

        points.add(new Point(screen, stage, teleporters, "PQ", PQPosition));
        points.add(new Point(screen, stage, teleporters, "infirmary", infirmaryPosition));
        points.add(new Point(screen, stage, teleporters, "north wing", northWingPosition));
        points.add(new Point(screen, stage, teleporters, "brig", brigPosition));
        points.add(new Point(screen, stage, teleporters, "engine", enginePosition));

    }

    public void update() {
        minimap.setPosition(camera.cam.position.x + mapOffset.x,
                camera.cam.position.y + mapOffset.y);
        for (Point point : points) {
            point.setPosition(camera.cam.position.x + mapOffset.x,
                    camera.cam.position.y + mapOffset.y);
        }
    }

    public void draw(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        for (Point point : points) {
            point.dispose();
        }
    }
}
