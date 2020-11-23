package com.team5.game.Tools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Constants {

    /*
    Constants is used to store values that are constant across all classes,
    so that they can be easily accessed using 'Constants.ATLAS' for example.
     */

    //ATLAS is the TexureAtlas holding all of the animated sprites.
    public static final TextureAtlas ATLAS = new TextureAtlas("textures.atlas");

    //Tile_SIZE refers to how many pixels per tile in the TileMap.
    //This is useful since LibGDX works in pixels so this way you can
    //calculate positions using tiles from the TileMap.
    public static final int TILE_SIZE = 16;

    //The GROUPs refer to the different collider groups.
    public static final short GROUP_PLAYER = -1;
    public static final short GROUP_WALLS = 1;

    //These refer to the resolution in pixels.
    public static final int CAMERA_WIDTH = 400;
    public static final int CAMERA_HEIGHT = 225;

    //MAX_HEALTH is the maximum health of the player.
    public static final int MAX_HEALTH = 3;
}
