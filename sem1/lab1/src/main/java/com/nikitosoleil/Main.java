package com.nikitosoleil;

public class Main {
    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        Thread demoThread = new Thread(() -> {
            while (app.getWalls().size() == 0) ;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }
            app.demo(10.0f);
        });
        demoThread.start();
        app.start();
    }
}
