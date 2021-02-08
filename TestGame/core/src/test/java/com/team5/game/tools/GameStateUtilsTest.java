package com.team5.game.tools;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;
import com.team5.game.sprites.Infiltrator;
import com.team5.game.sprites.NPC;
import com.team5.game.sprites.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameStateUtilsTest {

    @Mock
    Player mockPlayer;
    @Mock
    Infiltrator mockInfiltrator;
    @Mock
    Infiltrator mockInfiltrator2;

    Array<Infiltrator> infiltratorArray = new Array<>();
    Array<NPC> npcArray = new Array<>();

    GameState gameState;



    @BeforeEach
    void setup() {
        GameState.initialise();
        gameState = GameState.getInstance();

        infiltratorArray.add(mockInfiltrator);
        infiltratorArray.add(mockInfiltrator2);
    }

    @Test
    void createGameState_Difficulty() {
        Difficulty.setDifficulty(2);
        GameStateUtils.createGameState(mockPlayer);

        assertEquals(2, gameState.getDifficulty());
    }

    @Test
    void createGameState_Health() {
        when(mockPlayer.getHealth()).thenReturn(5);
        GameStateUtils.createGameState(mockPlayer);
        assertEquals(5, gameState.getHealth());
    }

    @Test
    void createGameState_Player() {
        when(mockPlayer.getX()).thenReturn(6.1f);
        when(mockPlayer.getY()).thenReturn(7.2f);
        GameStateUtils.createGameState(mockPlayer);

        assertEquals(6.1f, gameState.getPlayerX());
        assertEquals(7.2f, gameState.getPlayerY());
    }

}