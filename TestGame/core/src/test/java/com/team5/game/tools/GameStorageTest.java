package com.team5.game.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class GameStorageTest {

    private GameStorage gameStorage;
    private GameState gameState;

    @BeforeEach
    void setup(@TempDir Path tempDir) throws IOException {
        Path saveFile = tempDir.resolve("saveFile.txt");
        gameStorage = new GameStorage(new File(String.valueOf(saveFile)));
        GameState.initialise();
        gameState = GameState.getInstance();
        gameState.setDifficulty(2);
    }

    @Test
    void canSaveAndLoadGameState() throws IOException {
        gameStorage.save(gameState);
        gameState.setDifficulty(1);
        gameState = gameStorage.load();
        assertEquals(2, gameState.getDifficulty());
    }

    @Test
    void canSaveAndLoadGameStateTwice() throws IOException {
        gameStorage.save(gameState);
        gameStorage.save(gameState);
        gameState.setDifficulty(1);
        gameState = gameStorage.load();
        assertEquals(2, gameState.getDifficulty());
    }
}