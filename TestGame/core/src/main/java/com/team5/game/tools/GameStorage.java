package com.team5.game.tools;

import java.io.*;
import java.net.URISyntaxException;

public class GameStorage {

    private final File savedFile;

    public GameStorage() throws IOException, URISyntaxException {
        this(new File(System.getProperty("java.io.tmpdir") + File.separator + "auberSaveFile"));
    }

    public GameStorage(File savedFile) {
        this.savedFile = savedFile;
        System.out.println(savedFile.getAbsolutePath());
    }

    public void save(GameState gameState) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(savedFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(gameState);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public GameState load() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(savedFile);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        GameState loadedGameState = null;
        try {
            loadedGameState = (GameState) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        objectInputStream.close();
        fileInputStream.close();
        return loadedGameState;
    }
}
