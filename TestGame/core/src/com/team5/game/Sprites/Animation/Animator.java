package com.team5.game.Sprites.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

public class Animator {

    public Hashtable<String, Animation<TextureRegion>> animations;

    public Animation<TextureRegion> currentAnim;
    public TextureRegion currentSprite;

    float stateTime;
    public static final float frameDuration = 0.2f;

    TextureAtlas atlas;

    boolean looping = true;

    public Animator(TextureAtlas atlas, String idleKey, String idleRegion){
        this.atlas = atlas;

        animations = new Hashtable<>();

        animations.put(idleKey, new Animation<TextureRegion>(frameDuration, atlas.findRegions(idleRegion)));
        currentAnim = animations.get(idleKey);
        currentSprite = currentAnim.getKeyFrame(stateTime, true);
    }

    public TextureRegion getSprite(){
        currentSprite = currentAnim.getKeyFrame(stateTime, looping);
        stateTime += Gdx.graphics.getDeltaTime();
        return currentSprite;
    }

    public void play(String animKey){
        currentAnim = animations.get(animKey);
        looping = true;
    }

    public void play(String animKey, boolean loop){
        currentAnim = animations.get(animKey);
        looping = loop;
    }

    public void add(String animKey, String atlasRegion){
        animations.put(animKey, new Animation<TextureRegion>(frameDuration, atlas.findRegions(atlasRegion)));
    }

    public void flip(){
        currentSprite.flip(true, false);
    }

    public boolean isFlipped(){
        return currentSprite.isFlipX();
    }

}
