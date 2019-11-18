package com.nikitosoleil.battleship;

public class Logger {
    static final boolean ACTIVE = true;
    static void log(String s) {
        if (ACTIVE)
            System.out.println(s);
    }
}
