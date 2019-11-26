package com.nikitosoleil.battleship;

import org.junit.Test;

import static org.junit.Assert.*;

public class MiscTest {
    @Test
    public void inputManagerGetIndex() {
        assertEquals(-1, InputManager.getIndex(5, 10, 5, 0));
        assertEquals(-1, InputManager.getIndex(5, 10, 5, 15));
        assertEquals(2, InputManager.getIndex(5, 10, 5, 7.5f));
    }
}
