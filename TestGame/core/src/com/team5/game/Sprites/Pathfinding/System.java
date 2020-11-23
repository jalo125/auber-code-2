package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.team5.game.Tools.Constants;

public class System extends Room{

    /*
    System is used for the infiltrator pathfinding so they can
    access and break the systems.
     */

    boolean broken;

    //Positioning
    Vector2 spritePosition;

    //Sprites
    public TextureRegion currentSprite;

    //Audio
    Sound explosion = Gdx.audio.newSound(Gdx.files.internal("Audio/ploosh.wav"));

    //On means it's not been broken yet, off means it's broken.
    public TextureRegion onSprite;
    public TextureRegion offSprite;

    public System(String tag, float x, float y, String name,
                  float spriteX, float spriteY) {
        super(tag, x, y, name);
        this.spritePosition = new Vector2(spriteX * Constants.TILE_SIZE,
                spriteY * Constants.TILE_SIZE);

        setup();
    }

    //Sets up the system sprites
    public void setup(){
        broken = false;

        onSprite = Constants.ATLAS.findRegion("Systems/" + tag + "On");
        offSprite = Constants.ATLAS.findRegion("Systems/" + tag + "Off");

        currentSprite = onSprite;
    }

    //To be called every frame to draw the system sprites
    public void draw(SpriteBatch batch){
        if (broken){
            currentSprite = offSprite;
        }
        batch.draw(currentSprite, spritePosition.x, spritePosition.y);
        batch.draw(new Texture("Sprites/Minimap/Cursor.png"), x-10, y-10);
    }

    //destroy function is called when the infiltrator breaks the system
    public void destroy(){
        explosion.play(0.2f);
        broken = true;
    }

    public boolean getBroken(){
        return broken;
    }
}
