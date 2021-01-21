package com.team5.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Mouse{

    /*
    Mouse has become obsolete due to using buttons for mouse interactions,
    however if we want to make a custom mouse cursor we can still use this code,
    so I figured I wouldn't delete it all just yet.
     */

    //Collider
    private Body mouseBody;

    float colRadius = 5;

    //Movement
    public Vector2 position;

    public Mouse(World world){
        setupMouse(world);
    }

    public void setupMouse(World world){
        BodyDef bodDef = new BodyDef();
        bodDef.position.set(500, 320);
        bodDef.type = BodyDef.BodyType.KinematicBody;
        mouseBody = world.createBody(bodDef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(colRadius);
        shape.setPosition(new Vector2(0,0));
        fixDef.shape = shape;
        fixDef.isSensor = true;
        mouseBody.createFixture(fixDef).setUserData("Mouse");
    }

    public void update(CustomCamera camera){
        position = new Vector2(camera.cam.position.x - (camera.camWidth/2) + (Gdx.input.getX() * 0.25f),
                camera.cam.position.y + (camera.camHeight/2) - (Gdx.input.getY() * 0.25f));

        mouseBody.setTransform(position, 0f);
    }
}
