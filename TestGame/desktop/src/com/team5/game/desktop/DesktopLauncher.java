package com.team5.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.team5.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//Pack all of the textures into a texture atlas
		//REMOVE THIS CODE BEFORE RELEASING THE GAME {
		TexturePacker.Settings sets = new TexturePacker.Settings();
		sets.pot = true;
		sets.fast = true;
		sets.combineSubdirectories = true;
		sets.paddingX = 1;
		sets.paddingY = 1;
		sets.edgePadding = true;
		TexturePacker.process(sets, "Test", "./", "textures");
		// }

		new LwjglApplication(new MainGame(), config);
	}
}
