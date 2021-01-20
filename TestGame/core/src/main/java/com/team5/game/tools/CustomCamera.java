package com.team5.game.Tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team5.game.Sprites.Player;

public class CustomCamera {

    /*
    CustomCamera creates a camera for any given scene,
    it can then either stay static or follow the player
    using the follow function.
     */

    public OrthographicCamera cam;
    public Viewport port;

    public int camWidth = Constants.CAMERA_WIDTH;
    public int camHeight = Constants.CAMERA_HEIGHT;

    public CustomCamera(){
        cam = new OrthographicCamera();
        port = new FitViewport(camWidth, camHeight, cam);
        cam.position.set(camWidth/2, camHeight/2, 0);
    }

    //Makes it so the camera is on the player at the start
    public CustomCamera(Player player){
        cam = new OrthographicCamera();
        port = new FitViewport(camWidth, camHeight, cam);
        cam.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y, 0);
    }

    public void update(){
        cam.update();
    }

    //follow is used to make the camera follow the player
    public void follow(Player player){
        cam.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y, 0);
    }

}
