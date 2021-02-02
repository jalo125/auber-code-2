package com.team5.game.tools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Atlas {

    private static TextureAtlas textureAtlas;

    private Atlas() {
    }

    public static TextureAtlas getInstance() {
        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas("textures.atlas");
        }
        return textureAtlas;
    }

    // !! DO NOT USE EXCEPT FOR TESTING !!
    public static void overrideTextureAtlasWith(TextureAtlas textureAtlas) {
        Atlas.textureAtlas = textureAtlas;
    }
}