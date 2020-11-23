package com.team5.game.Sprites.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.team5.game.Tools.Constants;

import java.util.Hashtable;

public class Animator {

    /*
    Animator stores all the animations for a sprite and
    allows them to be easily accessed
     */

    public Hashtable<String, Animation<TextureRegion>> animations;

    public Animation<TextureRegion> currentAnim;
    public TextureRegion currentSprite;

    float stateTime;
    float timer;
    float frameDuration;

    boolean looping = true;

    public Animator(String idleKey, String idleRegion){
        this(idleKey, idleRegion, 0.2f);
    }

    public Animator(String idleKey, String idleRegion, float frameDuration){
        this.frameDuration = frameDuration;

        animations = new Hashtable<>();

        animations.put(idleKey, new Animation<TextureRegion>(frameDuration, Constants.ATLAS.findRegions(idleRegion)));
        currentAnim = animations.get(idleKey);
        currentSprite = currentAnim.getKeyFrame(stateTime, true);
    }

    public TextureRegion getSprite(){
        currentSprite = currentAnim.getKeyFrame(stateTime, looping);
        stateTime += Gdx.graphics.getDeltaTime();
        timer += Gdx.graphics.getDeltaTime();
        return currentSprite;
    }

    public void play(String animKey){
        play(animKey, true);
    }

    public void play(String animKey, boolean loop){
        currentAnim = animations.get(animKey);
        looping = loop;
        timer = 0f;
    }

    public void add(String animKey, String atlasRegion){
        animations.put(animKey, new Animation<TextureRegion>(frameDuration, Constants.ATLAS.findRegions(atlasRegion)));
    }

    public Animation getAnimation(String animKey){
        return animations.get(animKey);
    }



    public void flip(){
        currentSprite.flip(true, false);
    }

    public boolean isFlipped(){
        return currentSprite.isFlipX();
    }

    public boolean finished(){
        return (timer > currentAnim.getAnimationDuration());
    }

}
