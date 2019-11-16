package com.nikitosoleil;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Geometry;

public class MyCollisionListener implements PhysicsCollisionListener {
    private int count = 0;

    public void collision(PhysicsCollisionEvent event) {
        Geometry gA = (Geometry) event.getObjectA().getUserObject();
        Geometry gB = (Geometry) event.getObjectB().getUserObject();
        if (gA.getName().equals("Ball") && gB.getName().equals("Ball")) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}
