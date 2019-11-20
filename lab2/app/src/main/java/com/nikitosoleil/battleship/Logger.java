package com.nikitosoleil.battleship;

public class Logger {
    static final boolean ACTIVE = false;
    static void log(String s) {
        if (ACTIVE)
            System.out.println(s);
    }
}
