package com.team5.game.Sprites.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.team5.game.Tools.Constants;

public class AnimatedDrawable extends BaseDrawable {

    /*
    AnimatedDrawable is used to allow me to make animated
     */

    public Animation animation;

    TextureRegion keyFrame;
    float stateTime = 0;

    int tileSize;

    public AnimatedDrawable(TextureAtlas atlas, String key, String path, float frameDuration) {
        this(new Animator(atlas, key, path, frameDuration).getAnimation(key));
    }

    public AnimatedDrawable(Animation<TextureRegion> animation){
        this.animation = animation;

        keyFrame = animation.getKeyFrame(0);

        //I can use width since all of my sprite sizes are squares
        tileSize = keyFrame.getRegionWidth();
        this.setLeftWidth(tileSize/2);
        this.setRightWidth(tileSize/2);
        this.setBottomHeight(tileSize/2);
        this.setTopHeight(tileSize/2);
        this.setMinSize(tileSize, tileSize);
    }


    public void draw(Batch batch, float x, float y, float width, float height){

        stateTime += Gdx.graphics.getDeltaTime();
        keyFrame = (TextureRegion)animation.getKeyFrame(stateTime, true);

        batch.draw(keyFrame, x, y, tileSize, tileSize);
    }

}
