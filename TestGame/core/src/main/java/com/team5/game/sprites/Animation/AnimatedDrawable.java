package com.team5.game.Sprites.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.team5.game.Tools.Constants;

public class AnimatedDrawable extends BaseDrawable {

    /*
    AnimatedDrawable is used to make animated UI.

    Most UI elements use drawables for their textures.
     */

    //Base Animation
    public Animation animation;

    TextureRegion keyFrame;
    float stateTime = 0;

    int tileSize = Constants.TILE_SIZE;

    public AnimatedDrawable(String key, String path, float frameDuration) {
        this(new Animator(key, path, frameDuration).getAnimation(key));
    }

    public AnimatedDrawable(Animation<TextureRegion> animation){
        this.animation = animation;

        keyFrame = animation.getKeyFrame(0);

        //I can use width since all of my sprite sizes are squares
        this.setLeftWidth(tileSize/2);
        this.setRightWidth(tileSize/2);
        this.setBottomHeight(tileSize/2);
        this.setTopHeight(tileSize/2);
        this.setMinSize(tileSize, tileSize);
    }

    //Called when the animated UI is being drawn to the screen
    public void draw(Batch batch, float x, float y, float width, float height){

        stateTime += Gdx.graphics.getDeltaTime();
        keyFrame = (TextureRegion)animation.getKeyFrame(stateTime, true);

        batch.draw(keyFrame, x, y, tileSize, tileSize);
    }

}
