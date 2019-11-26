package com.nikitosoleil.battleship;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinatesTest {
    @Test
    public void creation() {
        Coordinates<Integer> c = new Coordinates<>(3, 5);
        assertEquals(c.x, (Integer) 3);
        assertEquals(c.y, (Integer) 5);
    }

    @Test
    public void cloning() {
        Coordinates<Integer> u = new Coordinates<>(3, 5);
        Coordinates<Integer> v = u.clone();
        assertEquals(v.x, (Integer) 3);
        assertEquals(v.y, (Integer) 5);
        assertNotEquals(u, v);
    }

    @Test
    public void equality() {
        Coordinates<Integer> u = new Coordinates<>(3, 5);
        Coordinates<Integer> v = u.clone();
        assertTrue(u.equals(v));
        assertTrue(v.equals(u));
        v.x = 1;
        assertFalse(u.equals(v));
        assertFalse(v.equals(u));
    }
}
