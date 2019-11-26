package nikitosoleil.parsing.interfaces;

public class Pair<T> {
    public T x, y;

    Pair(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Pair<T> clone() {
        return new Pair<T>(x, y);
    }
}
