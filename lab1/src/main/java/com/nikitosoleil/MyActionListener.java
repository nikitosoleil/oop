package com.nikitosoleil;

import com.jme3.app.SimpleApplication;
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
            Switch.light(app.getAmbientLight());
        } else if (name.equals("directionalLight") && !keyPressed) {
            Switch.light(app.getDirectionalLight());
        } else if (name.equals("shadowFilter") && !keyPressed) {
            Switch.filter(app.getDlsFilter());
        }
    }
};