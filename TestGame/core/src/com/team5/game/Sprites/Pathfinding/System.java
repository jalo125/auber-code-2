package com.team5.game.Sprites.Pathfinding;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.team5.game.Tools.Constants;

public class System extends Room{


    boolean broken;

    //Positioning
    Vector2 spritePosition;

    //Sprites
    public TextureRegion currentSprite;

    //On means it's not been broken yet, off means it's broken
    public TextureRegion onSprite;
    public TextureRegion offSprite;

    public System(String tag, float x, float y, String name,
                  float spriteX, float spriteY) {
        super(tag, x, y, name);
        this.spritePosition = new Vector2(spriteX * Constants.TILE_SIZE,
                spriteY * Constants.TILE_SIZE);

        setup();
    }

    public void setup(){
        broken = false;

        onSprite = Constants.ATLAS.findRegion("Systems/" + tag + "On");
        offSprite = Constants.ATLAS.findRegion("Systems/" + tag + "Off");

        currentSprite = onSprite;
    }

    public void draw(SpriteBatch batch){
        if (broken){
            currentSprite = offSprite;
        }
        batch.draw(currentSprite, spritePosition.x, spritePosition.y);
        batch.draw(new Texture("Sprites/Minimap/Cursor.png"), x-10, y-10);
    }

    public void destroy(){
        broken = true;
    }

    public boolean isBroken(){
        return broken;
    }
}
