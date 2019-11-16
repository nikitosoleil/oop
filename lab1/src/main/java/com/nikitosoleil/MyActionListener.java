package com.nikitosoleil;

import com.jme3.input.controls.ActionListener;

class MyActionListener implements ActionListener {
    MyApplication app;

    public MyActionListener(MyApplication app) {
        this.app = app;
    }

    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("shoot") && !keyPressed) {
            app.newBall();
        } else if (name.equals("ambientLight") && !keyPressed) {
            Switch.light(app.getEnlighter().getAmbientLight());
        } else if (name.equals("directionalLight") && !keyPressed) {
            Switch.light(app.getEnlighter().getDirectionalLight());
        } else if (name.equals("shadowFilter") && !keyPressed) {
            Switch.filter(app.getEnlighter().getDlsFilter());
        }
    }
};