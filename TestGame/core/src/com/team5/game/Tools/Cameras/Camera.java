package com.team5.game.Tools.Cameras;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.team5.game.Sprites.Player;

public class FollowCamera {

    public OrthographicCamera cam;
    public Viewport port;

    public int camWidth = 480;
    public int camHeight = 270;

    float smoothing = 0.1f;

    Player player;

    public FollowCamera(Player player){
        this.player = player;
        cam = new OrthographicCamera();
        port = new FitViewport(camWidth, camHeight, cam);
        cam.position.set(new Vector3(player.b2body.getPosition().x, player.b2body.getPosition().y, 0));
    }

    public void update(){
        cam.position.lerp(new Vector3(player.b2body.getPosition().x, player.b2body.getPosition().y, 0), smoothing);
        cam.update();
    }

}
