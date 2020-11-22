package com.team5.game.Tools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Constants {

    /*
    Constants is used to store values that are constant across all classes
     */

    public static final TextureAtlas ATLAS = new TextureAtlas("textures.atlas");

    public static final int TILE_SIZE = 16;

    public static final short GROUP_PLAYER = -1;
    public static final short GROUP_WALLS = 1;

    public static final int CAMERA_WIDTH = 400;
    public static final int CAMERA_HEIGHT = 225;

    public static final int MAX_HEALTH = 3;
}
