package com.team5.game.environment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrigTest {

    private Brig brig;

    @BeforeEach
    void setup() {
        brig = new Brig();
    }

    @Test
    void imprisonIncrementsPrisonerNumber() {
        brig.imprison();
        assertEquals(brig.getPrisoners(), 1);
        brig.imprison();
        assertEquals(brig.getPrisoners(), 2);
    }

    @Test
    void allCaught() {
        for (int i = 0; i < 7; i++) {
            brig.imprison();
        }
        assertFalse(brig.allCaught());
        brig.imprison();
        assertTrue(brig.allCaught());
    }
}