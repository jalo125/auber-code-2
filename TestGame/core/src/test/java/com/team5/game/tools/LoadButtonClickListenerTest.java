package com.team5.game.tools;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.team5.game.MainGame;
import com.team5.game.screens.PlayScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadButtonClickListenerTest {

    @Mock
    MainGame mockGame;
    @Mock
    PlayScreen mockPlayScreen;
    @Mock
    GameStorage mockGameStorage;
    @Mock
    Sound mockClick;
    @Mock
    InputEvent mockInputEvent;
    @Mock
    GameState mockGameState;
    @Mock
    Audio mockAudio;
    @Mock
    Files mockFiles;

    LoadButtonClickListener loadButtonClickListener;

    @BeforeEach
    void setup() throws IOException {
        Gdx.audio = mockAudio;
        Gdx.files = mockFiles;
        loadButtonClickListener = new LoadButtonClickListener(mockGame, mockPlayScreen, mockGameStorage, mockClick);
        when(mockGameStorage.load()).thenReturn(mockGameState);
    }

    @Test
    void gameIsLoadedWhenClicked() throws IOException {
        loadButtonClickListener.clicked(mockInputEvent, 1.0f, 1.0f);
        verify(mockGameStorage, times(1)).load();
    }

    @Test
    void soundIsPlayedWhenClicked() {
        loadButtonClickListener.clicked(mockInputEvent, 1.0f, 1.0f);
        verify(mockClick, times(1)).play(0.5f, 1.5f, 0);
    }

}