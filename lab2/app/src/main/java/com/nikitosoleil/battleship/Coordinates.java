package com.nikitosoleil.battleship;

public class Coordinates<T> {
    public T x, y;

    Coordinates(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates<T> clone() {
        return new Coordinates<T>(x, y);
    }

    public boolean equals(Coordinates<T> other) {
        return x == other.x && y == other.y;
    }
}
