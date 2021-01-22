package com.team5.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.team5.game.MainGame;

public class DesktopLauncher {

	/*
	Desktop Launcher is the class which actually launches
	the game and contains all of the settings surrounding that
	 */

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        //Pack all of the textures into a texture atlas
		/*REMOVE THIS CODE BEFORE RELEASING THE GAME {
		TexturePacker.Settings sets = new TexturePacker.Settings();C:\Program Files\Java\jre1.8.0_261\jdk-11.0.9.101
		sets.pot = true;
		sets.fast = true;
		sets.combineSubdirectories = true;
		sets.paddingX = 1;
		sets.paddingY = 1;
		sets.edgePadding = true;
		TexturePacker.process(sets, "Sprites/TexturePack", "./", "textures");
		}*/

        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;

        config.forceExit = false;

        config.fullscreen = true;

        new LwjglApplication(new MainGame(), config);
    }
}
